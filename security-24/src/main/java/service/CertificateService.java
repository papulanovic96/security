package service;

import entity.Certificate;
import entity.CertificateType;
import entity.TreeItem;

import java.util.ArrayList;

public interface CertificateService {

    Certificate findById(int id);

    Certificate findBySerialNumber(String serialNumber);

    ArrayList<Certificate> findAll();

    ArrayList<TreeItem> getTree();

    Certificate createRootCertificate(String subject);

    Certificate createSignedCertificate(String subject, String issuer, CertificateType certificateType);


}

