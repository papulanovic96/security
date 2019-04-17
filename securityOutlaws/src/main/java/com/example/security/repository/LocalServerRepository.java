package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.LocalServer;

@Repository
public interface LocalServerRepository extends JpaRepository<LocalServer, Long> {

}
