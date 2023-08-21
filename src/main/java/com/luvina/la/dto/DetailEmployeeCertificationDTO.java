/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * DetailEmployeeCertificationDTO.java, July 5, 2023 nvduc
 */
package com.luvina.la.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Chứa thông tin chi tiết về chứng chỉ của nhân viên.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailEmployeeCertificationDTO {
    private long certificationId;
    private String certificationName;
    private LocalDate certificationStartDate;
    private LocalDate certificationEndDate;
    private BigDecimal employeeCertificationScore;
}
