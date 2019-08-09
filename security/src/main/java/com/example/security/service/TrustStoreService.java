package com.example.security.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.repository.StoreNLoadCertificate;

@Service
public class TrustStoreService implements TrustStoreServiceIFace{

	@Autowired
	private StoreNLoadCertificate storenloadCertificate;
	
	@Autowired
	private CommunicationService communicationService;
	
	@Override
	public void update(String target, List<String> serialNumbers) {
		// TODO Auto-generated method stub
		X509Certificate[] cers = storenloadCertificate.findCertificates(serialNumbers);
        String[] paths = storenloadCertificate.findTarget(target);

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(4096, random);
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(null, null);

            keyStore.setKeyEntry("keys", keyGen.genKeyPair().getPrivate(), "zgadija".toCharArray(), cers);
            keyStore.store(new FileOutputStream(paths[0]), "zgadija".toCharArray());
            if (paths.length == 2) {
            	communicationService.sendTrustStorage(keyStore, paths[1]);
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException |
                KeyStoreException | IOException | CertificateException e) {
      
        }
	}

	@Override
	public Map<String, String> find(String target) {
		String[] paths = storenloadCertificate.findTarget(target);
        Map<String, String> result = new HashMap<>();
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(new FileInputStream(paths[0]), "zgadija".toCharArray());

            Certificate[] certs = keyStore.getCertificateChain("keys");
            int i = certs.length;
            for (Certificate cer : certs) {
                X509Certificate x509 = (X509Certificate) cer;
                result.put(x509.getSerialNumber().toString(), x509.getSubjectDN().getName());
            }
            return result;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
        	return null;
        }
		
    }
	

}
