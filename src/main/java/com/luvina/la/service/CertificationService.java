package com.luvina.la.service;

import com.luvina.la.entity.Certification;

import java.util.List;

/**
 * Cung cấp các phương thức truy xuất của certification
 * @author nvduc
 */
public interface CertificationService {
    List<Certification> getAllCertification();
}