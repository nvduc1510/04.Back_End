/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationController.java, July 5, 2023 nvduc
 */
package com.luvina.la.controller;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* Xử lý các thông tin liên quan đến certification
* @author nvduc
*/
@RestController
@RequestMapping("/certification")
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    /**
     * Lấy danh sách các chứng chỉ.
     *
     * @return Phản hồi HTTP chứa danh sách chứng chỉ hoặc thông báo lỗi.
     */
    @GetMapping()
    public ResponseEntity<?> getALLCertification(){
        List<Certification> certifications = certificationService.getAllCertification();
        if (certifications == null) {
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
        response.put("certification", certifications);
        return ResponseEntity.ok(response);
    }
}
