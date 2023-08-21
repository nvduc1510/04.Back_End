/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Employee.java, July 5, 2023 nvduc
 */
package com.luvina.la.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
/**
 * Thể hiện một nhân viên trong hệ thống.
 */
@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 5771173953267484096L;

    @Id
    @Column(name = "employee_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    @Column(name = "employee_birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employeeBirthDate;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "employee_telephone")
    private String employeeTelephone;

    @Column(name = "employee_login_id")
    private String employeeLoginId;

    @Column(name = "employee_login_password")
    private String employeeLoginPassword;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @JsonIgnore
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    private List<EmployeeCertification> employeeCertifications;
}
