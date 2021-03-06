package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.entity.Certificate;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findBySubject(String subject);

    Optional<Certificate>findBySerialNumber(String serialNumber);

    ArrayList<Certificate> findAllByIssuer(String issuer);
}
