package com.luvina.la.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 6868189362900231672L;

    private long employeeId; // ID của nhân viên.

    private String employeeName; // Tên của nhân viên.

    private LocalDate employeeBirthDate; // Ngày sinh của nhân viên.

    private String employeeEmail; // Email của nhân viên.

    private String employeeTelephone; // Số điện thoại của nhân viên.

    private String departmentName; // Tên bộ phận của nhân viên.

    private LocalDate endDate; // Ngày kết thúc

    private BigDecimal score; // Điểm số

    private String certificationName; // Tên chứng chỉ
}
