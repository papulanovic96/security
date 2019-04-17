package com.example.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.security.entity.LocalServer;

@Service
public interface LocalServerIFace {

	public List<LocalServer> findAll();
	public LocalServer findById(Long id);
	public LocalServer save(LocalServer server);
	public void remove(Long id);
}
