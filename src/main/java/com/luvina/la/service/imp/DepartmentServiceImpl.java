/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * DepartmentServiceImpl.java, July 5, 2023 nvduc
 */
package com.luvina.la.service.imp;
import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public List<Department> getAllDepartment() {
            List<Department> departments = departmentRepository.findAll();
            return  departments;
    }
}
