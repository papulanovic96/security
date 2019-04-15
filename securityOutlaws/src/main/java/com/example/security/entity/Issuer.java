package com.example.security.entity;

import java.security.PrivateKey;

import sun.security.x509.X500Name;

public class Issuer {

	private PrivateKey pKey;
	private X500Name xName;
	
	public Issuer() {}
	
	public Issuer(PrivateKey pKey, X500Name xName) {
		super();
		this.pKey = pKey;
		this.xName = xName;
	}

	public PrivateKey getpKey() {
		return pKey;
	}

	public void setpKey(PrivateKey pKey) {
		this.pKey = pKey;
	}

	public X500Name getxName() {
		return xName;
	}

	public void setxName(X500Name xName) {
		this.xName = xName;
	}
	
	
	
	
}
