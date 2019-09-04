package com.megatravel.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.entityDTO.ResponseUser;

@Service
public interface MainBackendService {

	@RequestMapping(value="/users/find/username={username}", method=RequestMethod.GET)
	public ResponseUser findUserByUsername(@PathVariable("username") String username);
	
}
