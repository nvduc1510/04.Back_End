/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeController.java, July 5, 2023 nvduc
 */
package com.luvina.la.controller;
import com.luvina.la.dto.AddEmployeeDTO;
import com.luvina.la.dto.DetailEmployeeDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.payload.ValidatorsException;
import com.luvina.la.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Xu ly cac logic lien quan den Employees
 *
 * @author nvduc
 */
@RestController
@ControllerAdvice
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Xử lý ngoại lệ do hợp lệ hóa dữ liệu (ValidatorsException) xảy ra.
     *
     * @param ex Ngoại lệ ValidatorsException đã xảy ra.
     * @return Phản hồi HTTP chứa thông tin về ngoại lệ.
     */
    private ResponseEntity<Map<String, Object>> handleValidatorException(ValidatorsException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        response.put("code", 500);
        message.put("code", ex.getCode());
        message.put("params", ex.getParams());
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Lấy danh sách các nhân viên dựa trên các tham số tùy chọn.
     *
     * @param employeeName         Tên nhân viên để tìm kiếm.
     * @param departmentId         ID của phòng ban để lọc theo .
     * @param ordEmployeeName      Tùy chọn sắp xếp theo tên nhân viên.
     * @param ordCertificationName Tùy chọn sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Tùy chọn sắp xếp theo ngày kết thúc.
     * @param offset               Vị trí bắt đầu của kết quả trả về .
     * @param limit                Số lượng kết quả trả về tối đa .
     * @return Phản hồi HTTP chứa danh sách nhân viên và thông tin phân trang.
     */
    @GetMapping("/")
    public ResponseEntity<?> getAllEmployee(
            @RequestParam(required = false, defaultValue = "") String employeeName,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false, defaultValue = "") String ordEmployeeName,
            @RequestParam(required = false, defaultValue = "") String ordCertificationName,
            @RequestParam(required = false, defaultValue = "") String ordEndDate,
            @RequestParam(required = false, defaultValue = "1") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        try {
            Page<EmployeeDTO> employeeDTOS = employeeService.getAllEmployeeDTO(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("totalRecords", employeeDTOS.getTotalElements());
            response.put("employees", employeeDTOS.getContent());

            return ResponseEntity.ok(response);
        } catch (ValidatorsException ex) {
            return handleValidatorException(ex);
        }
    }

    /**
     * Phương thức POST để thêm mới một nhân viên.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin của nhân viên mới
     * @return ResponseEntity chứa phản hồi với mã code, ID của nhân viên và thông điệp
     */
    @PostMapping()
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeDTO employeeDTO) {
        Employee employee;
        try {
            employee = this.employeeService.addEmployee(employeeDTO);
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            response.put("code", 200);
            response.put("employeeId", employee.getEmployeeId());
            message.put("code", "MSG001");
            message.put("params", new ArrayList<>());
            response.put("message",message);
            return ResponseEntity.ok(response);
        }catch (ValidatorsException ex){
            return handleValidatorException(ex);
        }
    }

    /**
     * Lấy thông tin của một nhân viên dựa trên ID nhân viên.
     *
     * @param employeeId ID của nhân viên cần lấy thông tin.
     * @return Phản hồi HTTP chứa thông tin chi tiết về nhân viên.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long employeeId) {
        DetailEmployeeDTO detailEmployeeDTO;
        try {
            detailEmployeeDTO = employeeService.getEmployeeById(employeeId);
        } catch (ValidatorsException ex) {
            return handleValidatorException(ex);
        }
        return ResponseEntity.ok(detailEmployeeDTO);

    }

    /**
     * Xóa thông tin của một nhân viên dựa trên ID nhân viên.
     *
     * @param employeeId ID của nhân viên cần xóa thông tin.
     * @return Phản hồi HTTP xác nhận việc xóa thông tin nhân viên.
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployeeById (@PathVariable long employeeId) {
        Optional <Employee> employee ;
        try {
            employee = employeeService.deleteEmployee(employeeId);
            List<String> messager = new ArrayList<>();
            messager.add("ユーザの削除が完了しました。");
            Map<String, Object> response  = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            response.put("code",200);
            response.put("employeeId", employeeId);
            message.put("code", "MSG003");
            message.put("params", messager);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (ValidatorsException ex) {
            return handleValidatorException(ex);
        }
    }

    /**
     * Cập nhật thông tin của một nhân viên dựa trên ID nhân viên.
     *
     * @param employeeId   ID của nhân viên cần cập nhật thông tin.
     * @param employeeDTO Thông tin nhân viên mới để cập nhật.
     * @return Phản hồi HTTP xác nhận việc cập nhật thông tin nhân viên.
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody AddEmployeeDTO employeeDTO) {
        try {
            employeeDTO.setValidatePassword(false);
            Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDTO);
            List<String> messages = new ArrayList<>();
            messages.add("ユーザの更新が完了しました。");
            Map<String, Object> response  = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            response.put("code",200);
            response.put("employeeId", employeeId);
            message.put("code", "MSG002");
            message.put("params", messages);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (ValidatorsException ex) {
          return handleValidatorException(ex);
        }
    }
}
