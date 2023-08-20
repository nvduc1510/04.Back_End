package com.luvina.la.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp `AddEmployeeCertificationDTO` đại diện cho đối tượng dùng để chứa thông tin cơ bản chứng chỉnh nhân viên.
 * @author nvduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeCertificationDTO {
    private String certificationId;
    private String certificationStartDate;
    private String certificationEndDate;
    private String employeeCertificationScore;
}
