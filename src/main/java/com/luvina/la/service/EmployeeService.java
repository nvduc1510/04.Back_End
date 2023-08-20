package com.luvina.la.service;

import com.luvina.la.dto.AddEmployeeDTO;
import com.luvina.la.dto.DetailEmployeeDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.payload.ValidatorsException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
public interface EmployeeService {
    /**
     * Phương thức này trả về một trang (Page) các đối tượng ListEmployeeDTO dựa trên các tiêu chí đã chỉ định.
     *
     * @param employeeName tên nhân viên
     * @param departmentId ID của phòng ban
     * @param ordEmployeeName thứ tự sắp xếp cho tên nhân viên
     * @param ordCertificationName thứ tự sắp xếp cho tên chứng chỉ
     * @param ordEndDate thứ tự sắp xếp cho ngày kết thúc
     * @param offset vị trí bắt đầu của các phần tử sẽ được trả về
     * @param limit số lượng tối đa các phần tử sẽ được trả về
     * @return đối tượng ResponseEntity chứa thông tin trả về: mã trạng thái, tổng số phần tử và nội dung danh sách ListEmployeeDTO
     */
    Page<EmployeeDTO> getAllEmployeeDTO(String employeeName, Long departmentId,
                                        String ordEmployeeName, String ordCertificationName,
                                        String ordEndDate, int offset, int limit) throws ValidatorsException;

    /**
     *Thuc hien add
     * @param  employeeDTO key cua message
     */
//    Employee addEmployees(EmployeeDTO employeeDTO);
    @Transactional
    Employee addEmployee(AddEmployeeDTO employeeDTO)throws ValidatorsException;

    /**
     * Lấy thông tin nhân viên dựa trên employeeId.
     *
     * @param employeeId Id của nhân viên cần lấy thông tin.
     * @return Đối tượng GetEmployeeDTO chứa thông tin nhân viên nếu tìm thấy.
     * @throws ValidatorsException Nếu không tìm thấy nhân viên, sẽ ném lỗi EmployeeResponse.
     */
    DetailEmployeeDTO getEmployeeById(long employeeId) throws ValidatorsException;

    @Transactional
    Optional<Employee> deleteEmployee(long employeeId) throws ValidatorsException;

    @Transactional
    Employee updateEmployee(Long employeeId,AddEmployeeDTO employeeDTO) throws ValidatorsException;


}
