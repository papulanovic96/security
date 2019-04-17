package com.example.security.service;

import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.springframework.stereotype.Service;

import com.example.security.entity.Certificate;
import com.example.security.entity.PrivateKeyNCertificate;
import com.example.security.enumType.CertificateType;

@Service
public interface CertificateServiceIFace {

	public List<Certificate> findAll();
	public void createRoot(X500Principal started);
	public PrivateKeyNCertificate[] createNewSigned(X500Principal persona, String izdavac, CertificateType tip);
}
