/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationService.java, July 5, 2023 nvduc
 */
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