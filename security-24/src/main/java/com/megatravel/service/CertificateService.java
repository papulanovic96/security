package com.megatravel.service;

import java.util.ArrayList;

import com.megatravel.entity.Certificate;
import com.megatravel.entity.CertificateType;
import com.megatravel.entity.TreeItem;

public interface CertificateService {

    Certificate findById(int id);

    Certificate findBySerialNumber(String serialNumber);

    ArrayList<Certificate> findAll();

    ArrayList<TreeItem> getTree();

    Certificate createRootCertificate(String subject);

    Certificate createSignedCertificate(String subject, String issuer, CertificateType certificateType);


}

