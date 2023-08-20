package com.luvina.la.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * Lớp `AddEmployeeDTO` đại diện cho đối tượng dùng để chứa thông tin cơ bản về một nhân viên.
 * @author nvduc
 */
@Data
@AllArgsConstructor
public class AddEmployeeDTO {
    private String employeeId;
    private String  departmentId;
    private String employeeName;
    private String employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginId;
    private String employeeLoginPassword;
    private boolean validatePassword;
    private List<AddEmployeeCertificationDTO> certifications;
}
