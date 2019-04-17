package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.security.entity.LocalServer;
import com.example.security.repository.LocalServerRepository;

@Service
public class LocalServerService implements LocalServerIFace{

	@Autowired
    private LocalServerRepository serverRepo;

    public List<LocalServer> findAll() {
        return serverRepo.findAll();
    }

    public LocalServer findById(Long id) {
            return serverRepo.findById(id).orElse(null);  
    }

    public LocalServer save(LocalServer server) {
    	return serverRepo.save(server);

    }

    public void remove(Long id) {
    	serverRepo.deleteById(id);
    }
}
