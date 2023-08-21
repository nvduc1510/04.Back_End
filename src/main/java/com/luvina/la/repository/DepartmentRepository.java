/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * DepartmentRepository.java, July 5, 2023 nvduc
 */
package com.luvina.la.repository;
import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Giao diện xử lý truy vấn dữ liệu liên quan đến bảng phòng ban.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentId(long departmentId);
}
