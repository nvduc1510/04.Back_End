package com.luvina.la.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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
