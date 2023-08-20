package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
