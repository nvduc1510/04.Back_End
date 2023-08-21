/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeServiceImpl.java, July 5, 2023 nvduc
 */
package com.luvina.la.service.imp;
import com.luvina.la.dto.*;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.Validators;
import com.luvina.la.payload.ValidatorsException;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.EmployeeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;
    @Autowired
    Validators validators;

    /**
     * Phương thức này trả về một trang (Page) các đối tượng ListEmployeeDTO.
     *
     * @param employeeName         tên nhân viên
     * @param departmentId         ID của phòng ban
     * @param ordEmployeeName      thứ tự sắp xếp cho tên nhân viên
     * @param ordCertificationName thứ tự sắp xếp cho tên chứng chỉ
     * @param ordEndDate           thứ tự sắp xếp cho ngày kết thúc
     * @param offset               vị trí bắt đầu của các phần tử
     * @param limit                số lượng tối đa các phần tử
     * @return đối tượng Page chứa các đối tượng ListEmployeeDTO
     */
    @Override
    public Page<EmployeeDTO> getAllEmployeeDTO(String employeeName, Long departmentId,
                                               String ordEmployeeName, String ordCertificationName,
                                               String ordEndDate, int offset, int limit) throws ValidatorsException {
        try {
            String escapeEmployeeName = employeeName != null ? employeeName.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_")
                    .replace(";", "\\;") : null;

            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<EmployeeDTO> employeeDTOS = employeeRepository.getListEmployeeDTO(escapeEmployeeName, departmentId, ordEmployeeName,
                    ordCertificationName, ordEndDate, pageable);
            return employeeDTOS;
        } catch (Exception e) {
            throw new ValidatorsException();
        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Employee addEmployee(AddEmployeeDTO employeeDTO) throws ValidatorsException {
        Employee employee = new Employee();
        List<EmployeeCertification> employeesCertificationsList = new ArrayList<>();
        try {
            employee.setEmployeeLoginId(validators.validEmployeeLoginId(employeeDTO.getEmployeeLoginId()));
            // kiem tra employeeLoginId có tồn tai hay không
            if (this.employeeRepository.existsByEmployeeLoginId(employee.getEmployeeLoginId())) {
                List<String> params = new ArrayList<>();
                params.add("「アカウント名」は既に存在しています。");
                throw new ValidatorsException("ER003", params);
            }

            Department department = new Department();
            department.setDepartmentId(validators.validateDepartmentId(employeeDTO.getDepartmentId()));
            employee.setDepartment(department);

            // kiem tra departmentId ton tai
            if (!this.departmentRepository.existsByDepartmentId(department.getDepartmentId())) {
                List<String> params = new ArrayList<>();
                params.add("「グループ」は存在していません。");
                throw new ValidatorsException("ER004", params);
            }
            employee.setEmployeeName(validators.validEmployeeName(employeeDTO.getEmployeeName()));
            employee.setEmployeeTelephone(validators.validatePhoneNumber(employeeDTO.getEmployeeTelephone()));
            employee.setEmployeeNameKana(validators.validNameKatakana(employeeDTO.getEmployeeNameKana()));
            employee.setEmployeeEmail(validators.validateEmail(employeeDTO.getEmployeeEmail()));
            employee.setEmployeeBirthDate(validators.validateBirthDay(employeeDTO.getEmployeeBirthDate()));
            employee.setEmployeeLoginPassword(validators.validatePassword(employeeDTO.getEmployeeLoginPassword()));
//
//            String password = employeeDTO.getEmployeeLoginPassword();
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            employee.setEmployeeLoginPassword(validators.validatePassword(passwordEncoder.encode(password)));

            List<AddEmployeeCertificationDTO> employeeCertificationDTO = employeeDTO.getCertifications();
            if (!employeeCertificationDTO.isEmpty()) {
                for (AddEmployeeCertificationDTO certificationRequests : employeeCertificationDTO) {
                    EmployeeCertification employeesCertification = new EmployeeCertification();
                    Certification certification = new Certification();
                    certification.setCertificationId(validators.validateCertificationId(certificationRequests.getCertificationId()));
                    // kiem tra certificationId co ton tai hay khong
                    if (!certificationRepository.existsByCertificationId(certification.getCertificationId())) {
                        List<String> params = new ArrayList<>();
                        params.add("資格");
                        throw new ValidatorsException("ER004", params);
                    }
                    employeesCertification.setEmployee(employee);
                    employeesCertification.setCertification(certification);
                    employeesCertification.setStartDate(validators.validateStartDate(certificationRequests.getCertificationStartDate()));
                    employeesCertification.setEndDate(validators.validateEndDate(certificationRequests.getCertificationEndDate(),
                            employeesCertification.getStartDate()));
                    employeesCertification.setScore(validators.validateScore(certificationRequests.getEmployeeCertificationScore()));
                    employeesCertificationsList.add(employeesCertification);
                }
            }
        } catch (ValidatorsException valid) {
            throw valid;
        }
        this.employeeRepository.save(employee);
        this.employeeCertificationRepository.saveAll(employeesCertificationsList);

        return employee;
    }

    @Override
    public DetailEmployeeDTO getEmployeeById(long employeeId) throws ValidatorsException {
        boolean isEmployeeIdExists = employeeRepository.existsById(employeeId);
        if (!isEmployeeIdExists) {
            List<String> params = new ArrayList<>();
            params.add("該当するユーザは存在していません。");
            throw new ValidatorsException("ER013", params);
        }
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            List<String> params = new ArrayList<>();
            params.add("「画面項目名」を入力してください");
            return new ValidatorsException("ER001", params);
        });
        DetailEmployeeDTO detailEmployeeDTO = new DetailEmployeeDTO();
        detailEmployeeDTO.setCode(200);
        detailEmployeeDTO.setEmployeeId(employee.getEmployeeId());
        detailEmployeeDTO.setEmployeeName(employee.getEmployeeName());
        detailEmployeeDTO.setEmployeeBirthDate(employee.getEmployeeBirthDate());
        detailEmployeeDTO.setDepartmentId(String.valueOf(employee.getDepartment().getDepartmentId()));
        detailEmployeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        detailEmployeeDTO.setEmployeeEmail(employee.getEmployeeEmail());
        detailEmployeeDTO.setEmployeeTelephone(employee.getEmployeeTelephone());
        detailEmployeeDTO.setEmployeeNameKana(employee.getEmployeeNameKana());
        detailEmployeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
        List<EmployeeCertification> employeeCertifications = employee.getEmployeeCertifications();
        if (employeeCertifications != null && !employeeCertifications.isEmpty()) {
            List<DetailEmployeeCertificationDTO> detailEmployeeCertificationDTOS = new ArrayList<>();
            for (EmployeeCertification employeeCertification : employeeCertifications) {
                if (employeeCertification.getCertification() != null) { // Check if certification is not null
                    DetailEmployeeCertificationDTO detailDTO = new DetailEmployeeCertificationDTO();
                    detailDTO.setCertificationId(employeeCertification.getCertification().getCertificationId());
                    detailDTO.setCertificationName(employeeCertification.getCertification().getCertificationName());
                    detailDTO.setCertificationStartDate(employeeCertification.getStartDate());
                    detailDTO.setCertificationEndDate(employeeCertification.getEndDate());
                    detailDTO.setEmployeeCertificationScore(employeeCertification.getScore());
                    detailEmployeeCertificationDTOS.add(detailDTO);
                }
            }
            detailEmployeeDTO.setCertifications(detailEmployeeCertificationDTOS);
        }
        return detailEmployeeDTO;
    }

    @Override
    public Optional<Employee> deleteEmployee(long employeeId) throws ValidatorsException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        // Check xem employeeId có tồn tại hay không
        if (employee.isEmpty()) {
            List<String> params = new ArrayList<>();
            params.add("該当するユーザは存在していません。");
            throw new ValidatorsException("ER014", params);
        } else {

        }
//        try {
            Employee employees = employee.get();
            employeeRepository.deleteById(employeeId);
//        }
//        catch (DataAccessException e) {
//            if(e.getCause() instanceof ConnectException) {
//                List<String> params = new ArrayList<>();
//                params.add("システムエラーが発生しました。");
//                throw new EmployeeResponse("ER015", params);
//            }
//        }
        return employee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long employeeId, AddEmployeeDTO employeeDTO) throws ValidatorsException {
        Employee employees = employeeRepository.findByEmployeeId(employeeId).orElseThrow(() -> {
            // Nếu employeeId không tồn tại thì trả về lỗi dưới
            List<String> params = new ArrayList<>();
            params.add("該当するユーザは存在していません。");
            return new ValidatorsException("ER013", params);
        });
        Department department = departmentRepository.findById(Long.parseLong(employeeDTO.getDepartmentId())).orElseThrow(() -> {
            List<String> params = new ArrayList<>();
            params.add("「グループ」を入力してください");
            return new ValidatorsException("ER002", params);
        });
        department.setDepartmentId(validators.validateDepartmentId(employeeDTO.getDepartmentId()));
        employees.setDepartment(department);
        employees.setEmployeeName(validators.validEmployeeName(employeeDTO.getEmployeeName()));
        employees.setEmployeeTelephone(validators.validatePhoneNumber(employeeDTO.getEmployeeTelephone()));
        employees.setEmployeeNameKana(validators.validNameKatakana(employeeDTO.getEmployeeNameKana()));
        employees.setEmployeeEmail(validators.validateEmail(employeeDTO.getEmployeeEmail()));
        employees.setEmployeeBirthDate(validators.validateBirthDay(employeeDTO.getEmployeeBirthDate()));
        // Check xem password có nhập hay không
        String newPassword = employeeDTO.getEmployeeLoginPassword();
        if(newPassword != null && !newPassword.isEmpty() ) {
            employees.setEmployeeLoginPassword(validators.validPasswordUpdate(employeeDTO.getEmployeeLoginPassword()));
        } else {
            employees.setEmployeeLoginPassword(employees.getEmployeeLoginPassword());
        }
        List<EmployeeCertification> oldCertifications = employeeCertificationRepository.findByEmployee(employees);
        if (!oldCertifications.isEmpty()) {
            employeeCertificationRepository.deleteAll(oldCertifications);
        }
        List<EmployeeCertification> employeeCertifications = new ArrayList<>();
        employees.setEmployeeCertifications(employeeCertifications);
        // Tạo mới employee certifications
        List<AddEmployeeCertificationDTO> addEmployeeCertificationDTOS = employeeDTO.getCertifications();
        if (addEmployeeCertificationDTOS != null && !addEmployeeCertificationDTOS.isEmpty()) {
            for (AddEmployeeCertificationDTO addEmployeeCertificationDTO : addEmployeeCertificationDTOS) {
                EmployeeCertification employeesCertification = new EmployeeCertification();
                Certification certification = new Certification();
                certification.setCertificationId(validators.validateCertificationId(addEmployeeCertificationDTO.getCertificationId()));
                // Kiểm tra certification tồn tại hay không
                if (!certificationRepository.existsByCertificationId(certification.getCertificationId())) {
                    List<String> params = new ArrayList<>();
                    params.add("「資格」は存在していません。");
                    throw new ValidatorsException("ER004", params);
                }
                employeesCertification.setEmployee(employees);
                employeesCertification.setCertification(certification);
                employeesCertification.setStartDate(validators.validateStartDate(addEmployeeCertificationDTO.getCertificationStartDate()));
                employeesCertification.setEndDate(validators.validateEndDate(addEmployeeCertificationDTO.getCertificationEndDate(),
                        employeesCertification.getStartDate()));
                employeesCertification.setScore(validators.validateScore(addEmployeeCertificationDTO.getEmployeeCertificationScore()));
                employeeCertifications.add(employeesCertification);
            }
            employees.setEmployeeCertifications(employeeCertifications);
            employeeCertificationRepository.saveAll(employeeCertifications);
        }
        return employeeRepository.save(employees);
    }

}
