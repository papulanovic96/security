package com.example.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.security.converter.CertificateConverter;
import com.example.security.entity.Certificate;
import com.example.security.entity.TrustStorage;
import com.example.security.service.TrustStoreService;

@Controller
@RequestMapping("/truststorage")
public class TrustStorageController {
	
	@Autowired
	private TrustStoreService tService;
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Certificate>> getTrustStorage(@PathVariable String id) {
		return new ResponseEntity<List<Certificate>>(CertificateConverter.fromMapToCertificate(tService.find(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody TrustStorage param) {
		tService.update(param.getTarget(), param.getSerialNumbers());
        return new ResponseEntity<>("Trust storage updated", HttpStatus.OK);
	}



}
