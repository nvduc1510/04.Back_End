package com.luvina.la.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailEmployeeDTO {
    private long code; // Mã nhân viên cụ thể.

    private long employeeId; // ID của nhân viên.

    private String employeeName; // Tên của nhân viên.

    private LocalDate employeeBirthDate; // Ngày sinh của nhân viên.

    private String departmentId; // Mã bộ phận của nhân viên

    private String departmentName; // Tên bộ của phận  nhân .

    private String employeeEmail; // Email của nhân viên.

    private String employeeTelephone; // Số điện thoại của nhân viên.

    private String employeeNameKana; // Tên Katakana của nhân viên.

    private String employeeLoginId; // Tên đăng nhập của nhân viên.

    private List<DetailEmployeeCertificationDTO> certifications; // Danh sách các chứng chỉ của nhân viên .

}
