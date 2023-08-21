/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeCertification.java, July 5, 2023 nvduc
 */
package com.luvina.la.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
/**
 * Thể hiện một chứng chỉ của nhân viên trong hệ thống.
 */
@Entity
@Table(name = "employees_certifications")
@Data
public class EmployeeCertification {
    @Id
    @Column(name = "employee_certification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeCertificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "certification_id", referencedColumnName = "certification_id")
    @JsonIgnore
    private Certification certification;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "score", columnDefinition = "DECIMAL")
    private BigDecimal score;
}
