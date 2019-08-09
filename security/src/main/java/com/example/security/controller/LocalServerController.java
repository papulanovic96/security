package com.example.security.controller;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.entity.LocalServer;
import com.example.security.service.LocalServerService;

@Controller
@RequestMapping("/localserver")
public class LocalServerController {

	@Autowired
	private LocalServerService localServerService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResponseEntity<List<LocalServer>> findAll() {
        return new ResponseEntity<>(localServerService.findAll(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<LocalServer> save(@RequestBody LocalServer server) {
        return new ResponseEntity<>(localServerService.save(server), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id) {
        localServerService.remove(Long.parseLong(id));
        return new ResponseEntity<>("Server successfully deleted!", HttpStatus.OK);
    }
	
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	public ResponseEntity<LocalServer> findById(@PathVariable String id) {
        return new ResponseEntity<LocalServer>(localServerService.findById(Long.parseLong(id)), HttpStatus.FOUND);
    }

}
