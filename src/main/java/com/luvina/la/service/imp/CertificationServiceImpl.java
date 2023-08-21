/*
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationServiceImpl.java, July 5, 2023 nvduc
 */
package com.luvina.la.service.imp;
import com.luvina.la.entity.Certification;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Xu ly ve cac thông tin liên quan đến Certification
 * @author nvduc
 */
@Service
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    private CertificationRepository certificationsRepository;
    @Override
    public List<Certification> getAllCertification() {
        return (List<Certification>) certificationsRepository.findAll();
    }
}