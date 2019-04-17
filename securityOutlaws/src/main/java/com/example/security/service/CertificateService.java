package com.example.security.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.security.entity.Certificate;
import com.example.security.entity.PrivateKeyNCertificate;
import com.example.security.enumType.CertificateType;
import com.example.security.repository.CerificateRepository;
import com.example.security.repository.StoreNLoadCertificate;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

@Service
public class CertificateService implements CertificateServiceIFace{

	
	@Autowired
	private CerificateRepository certificateRepo;
	
	@Autowired
	private StoreNLoadCertificate storenload;
	
    private String server;

	@Override
	public List<Certificate> findAll() {
		return certificateRepo.findAll();
	}

	@Override
	public void createRoot(X500Principal started) {
		CertAndKeyGen gen = generateKeyPair();
        CertificateExtensions exts = new CertificateExtensions();
        try {
            exts.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(true, -1));
            X509Certificate certificate = gen.getSelfCertificate(X500Name.asX500Name(started),
                    new Date(), (long) 365 * 24 * 3600, exts);
            PrivateKeyNCertificate ck = new PrivateKeyNCertificate(gen.getPrivateKey(), certificate);
            storenload.store(new PrivateKeyNCertificate[]{ck}, server, CertificateType.ROOT, "keys", "zgadija");
            certificateRepo.save(new Certificate(null, ck.getCertificate().getSerialNumber().toString(),
                    ck.getCertificate().getSubjectDN().getName(), true));
        } catch (IOException | CertificateException | InvalidKeyException | SignatureException
                | NoSuchAlgorithmException | NoSuchProviderException e) {
            
        }
	}

	@Override
	public PrivateKeyNCertificate[] createNewSigned(X500Principal persona, String izdavac, CertificateType tip) {
		PrivateKeyNCertificate[] chain = storenload.load("keys", "zgadija", izdavac, server, CertificateType.MIDDLEMAN);
        Principal issuerData = chain[0].getCertificate().getSubjectDN();
        String issuerSigAlg = chain[0].getCertificate().getSigAlgName();

        CertAndKeyGen sub = generateKeyPair();
        try {
            X509Certificate certificate = sub.getSelfCertificate(X500Name.asX500Name(persona),
                    (long) 365 * 24 * 3600);
            X509CertInfo info = new X509CertInfo(certificate.getTBSCertificate());
            info.set(X509CertInfo.ISSUER, issuerData);

            CertificateExtensions exts = new CertificateExtensions();
            if (tip == CertificateType.USER) {
                exts.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(false, -1));
            } else {
                exts.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(true, -1));
            }

            info.set(X509CertInfo.EXTENSIONS, exts);
            X509CertImpl outCert = new X509CertImpl(info);
            outCert.sign(chain[0].getpKey(), issuerSigAlg);

            chain[0].setpKey(null); // do not store privet key of issuer
            PrivateKeyNCertificate[] expendedChain = new PrivateKeyNCertificate[chain.length + 1];
            expendedChain[0] = new PrivateKeyNCertificate(sub.getPrivateKey(), outCert);
            System.arraycopy(chain, 0, expendedChain, 1, chain.length);
            storenload.store(expendedChain, server, tip, "keys", "zgadija");
            certificateRepo.save(new Certificate(null, expendedChain[0].getCertificate().
                    getSerialNumber().toString(), expendedChain[0].getCertificate()
                    .getSubjectDN().getName(), true));
            return expendedChain;
        } catch (CertificateException | InvalidKeyException | SignatureException |
                NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
        	return null;
        } catch (DataIntegrityViolationException e) {
            return null;
        }
	}
	
	private CertAndKeyGen generateKeyPair() {
        try {
            CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
            keyGen.generate(4096);
            keyGen.setRandom(SecureRandom.getInstance("SHA1PRNG", "SUN"));
            return keyGen;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e) {
        	return null;
        }
    }

    public void setServer(String server) {
        this.server = server;
    }

}
