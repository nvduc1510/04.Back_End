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

    private ResponseEntity<Map<String, Object>> handleValidatorException(ValidatorsException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        response.put("code", 500);
        message.put("code", ex.getCode());
        message.put("params", ex.getParams());
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

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
