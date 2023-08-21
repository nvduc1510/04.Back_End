/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * EmployeeRepository.java, July 5, 2023 nvduc
 */
package com.luvina.la.repository;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * Giao diện kho lưu trữ để truy cập và quản lý dữ liệu EmployeeRepository trong cơ sở dữ liệu.
 * @author nvduc
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);
    Optional<Employee> findByEmployeeId(Long employeeId);
    @Query("SELECT new com.luvina.la.dto.EmployeeDTO(e.employeeId, e.employeeName,e.employeeBirthDate," +
            " e.employeeEmail, e.employeeTelephone, d.departmentName," +
            "ec.endDate, ec.score, c.certificationName) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.employeeCertifications ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%')) " +
            "AND (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "ORDER BY " +
            "CASE WHEN :ordEmployeeName = 'ASC' THEN e.employeeName END ASC, " +
            "CASE WHEN :ordEmployeeName = 'DESC' THEN e.employeeName END DESC, " +
            "CASE WHEN :ordCertificationName = 'ASC' THEN c.certificationName END ASC, " +
            "CASE WHEN :ordCertificationName = 'DESC' THEN c.certificationName END DESC, " +
            "CASE WHEN :ordEndDate = 'ASC' THEN ec.endDate END ASC, " +
            "CASE WHEN :ordEndDate = 'DESC' THEN ec.endDate END DESC" )

    /**
     * Lấy danh sách DTO của nhân viên dựa trên các thông tin truy vấn.
     *
     * @param employeeName         Tên của nhân viên.
     * @param departmentId         ID của phòng ban.
     * @param ordEmployeeName     Thứ tự sắp xếp theo tên nhân viên.
     * @param ordCertificationName Thứ tự sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Thứ tự sắp xếp theo ngày kết thúc.
     * @param pageable             Thông tin phân trang.
     * @return Trang chứa danh sách DTO của nhân viên.
     */
    Page<EmployeeDTO> getListEmployeeDTO(
            @Param("employeeName") String employeeName,
            @Param("departmentId") Long departmentId,
            @Param("ordEmployeeName") String ordEmployeeName,
            @Param("ordCertificationName") String ordCertificationName,
            @Param("ordEndDate") String ordEndDate,
            Pageable pageable);

    /**
     * Kiểm tra sự tồn tại của một nhân viên dựa trên employeeLoginId.
     *
     * @param employeeLoginId Tên đăng nhập của nhân viên.
     * @return `true` nếu tồn tại, `false` nếu không tồn tại.
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);
}
