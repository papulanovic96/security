package com.example.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.security.entity.Certificate;

public interface CerificateRepository extends JpaRepository<Certificate, Long> {

	 @Query(value = "SELECT * FROM certificate", nativeQuery = true)
	 List<Certificate> findItAll();
}
