package com.example.security.service;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.security.repository.CerificateRepository;


public class Verification {

	@Autowired
    private CerificateRepository repo;

    private boolean checkDate(X509Certificate cert) {
        Date startDate = cert.getNotBefore();
        Date endDate = cert.getNotAfter();

        Date today = new Date();

        return today.after(startDate) && today.before(endDate);
    }

    private boolean verifyCertificate(X509Certificate cert) {
        boolean isActive = repo.findItAll().get(0).isStatus();
        boolean isValidDate = checkDate(cert);
        return isActive && isValidDate;
    }

    private boolean verifySignature(X509Certificate certificate, X509Certificate parentCertificate) {
        try {
            certificate.verify(parentCertificate.getPublicKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public byte[] verifyCertificateChain(X509Certificate[] certificateList, String folderAddress) {
        KeyStoreRService keyStoreReader = new KeyStoreRService();

        PrivateKey privateKey = keyStoreReader.readPrivateKey(folderAddress,
                "keyStorePassword", "myKey", "password");
        boolean valid = true;
        for (int i = 0; i < certificateList.length; i++) {
            X509Certificate cert = certificateList[i];
            if (!verifyCertificate(cert) && verifySignature(cert, certificateList[i + 1]))
                valid = false;
        }
        if (valid) {
            Signature sig = null;
            try {
                sig = Signature.getInstance("SHA1withRSA");
                sig.initSign(privateKey);
                sig.update("Valid".getBytes());
                return sig.sign();
            } catch (Exception e) {
                return new byte[0];
            }
        }
        return new byte[0];
    }
}
