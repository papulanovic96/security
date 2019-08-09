package com.example.security.entity;

import java.security.Key;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class PrivateKeyNCertificate {

	private PrivateKey pKey;
	private X509Certificate certificate;

	public PrivateKeyNCertificate() {}

	public PrivateKeyNCertificate(PrivateKey pKey, X509Certificate certificate) {
		super();
		this.pKey = pKey;
		this.certificate = certificate;
	}

	public PrivateKey getpKey() {
		return pKey;
	}

	public void setpKey(PrivateKey pKey) {
		this.pKey = pKey;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	
    public static PrivateKeyNCertificate[] toChain(Certificate[] certificates, Key privateKey) {
    	PrivateKeyNCertificate[] chain = new PrivateKeyNCertificate[certificates.length];
        boolean passedLeaf = false;
        for (int i = 0; i < certificates.length; i++) {
            if (!passedLeaf) {
                chain[i] = new PrivateKeyNCertificate((PrivateKey) privateKey, (X509Certificate) certificates[i]);
                passedLeaf = true;
            } else {
                chain[i] = new PrivateKeyNCertificate(null, (X509Certificate) certificates[i]);
            }
        }
        return chain;
    }

    public static X509Certificate[] toChain(PrivateKeyNCertificate[] certificates) {
        X509Certificate[] chain = new X509Certificate[certificates.length];
        for (int i = 0; i < certificates.length; i++) {
            chain[i] = certificates[i].getCertificate();
        }
        return chain;
    }
	
}
