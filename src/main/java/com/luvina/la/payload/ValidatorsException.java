package com.luvina.la.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Lớp ngoại lệ (exception) cho các trường hợp xác thực không hợp lệ.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorsException extends Exception {

    private String code; // Mã lỗi đại diện cho loại lỗi cụ thể.
    private List<String> params; // Danh sách tham số dùng để xây dựng thông báo lỗi.
    /**
     * Constructor của lớp `ValidatorsException` với mã lỗi được chỉ định.
     * @param code Mã lỗi đại diện cho loại lỗi cụ thể.
     */
    public ValidatorsException(String code) {
        this.code = code;
    }

}