package com.example.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface TrustStoreServiceIFace {

	public void update(String target, List<String> serialNumbers);
	public Map<String, String> find(String target);
}
