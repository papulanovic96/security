package com.example.security.entityDTO;

import java.io.Serializable;

import com.example.security.enumType.CertificateType;

public class CertificateGenerateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String x500Name;

    private String server;

    private IssuerDTO issuer;

    private String destination;

    private CertificateType type;

    public CertificateGenerateDTO() {
    }

    public CertificateGenerateDTO(String x500Name, String server, IssuerDTO issuer, String destination, CertificateType type) {
        this.x500Name = x500Name;
        this.server = server;
        this.issuer = issuer;
        this.destination = destination;
        this.type = type;
    }

    public String getX500Name() {
        return x500Name;
    }

    public void setX500Name(String x500Name) {
        this.x500Name = x500Name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public IssuerDTO getIssuer() {
        return issuer;
    }

    public void setIssuer(IssuerDTO issuer) {
        this.issuer = issuer;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }
}