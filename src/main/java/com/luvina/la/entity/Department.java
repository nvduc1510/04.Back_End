/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * Department.java, July 5, 2023 nvduc
 */
package com.luvina.la.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
/**
 * Thể hiện một bộ phận trong hệ thống.
 */
@Entity
@Data
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_name", nullable = false, length = 50)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Employee> employees;
}
