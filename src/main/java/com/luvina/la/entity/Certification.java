/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Certification.java, July 5, 2023 nvduc
 */
package com.luvina.la.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
/**
 * Thể hiện một chứng chỉ trong hệ thống.
 */
@Entity
@Data
@Table(name = "certifications")
public class Certification {
    @Id
    @Column(name = "certification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    @Column(name = "certification_name", nullable = false, length = 50)
    private String certificationName;

    @Column(name = "certification_level")
    private int certificationLevel;

    @OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeCertification> employeeCertifications;
}
