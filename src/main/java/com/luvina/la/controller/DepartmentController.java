package com.luvina.la.controller;

import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * lay tat ca phong ban
     */
    @GetMapping
    public ResponseEntity<?> getAllDepartment() {
        List<Department> department = departmentService.getAllDepartment();
        if (department == null) {
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            List<String> mes = new ArrayList<>();
            mes.add("システムエラーが発生しました。");
            response.put("code", 500);
            message.put("code", "ER023");
            message.put("params", mes);
            response.put("message", message);
            return ResponseEntity.ok(response);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("department", department);
        return ResponseEntity.ok(response);
    }
}
