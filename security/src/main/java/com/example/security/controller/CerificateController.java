package com.example.security.controller;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.entity.Certificate;
import com.example.security.entity.PrivateKeyNCertificate;
import com.example.security.entityDTO.CertificateGenerateDTO;
import com.example.security.enumType.CertificateType;
import com.example.security.service.CertificateService;
import com.example.security.service.CommunicationService;

@Controller
@RequestMapping("/certificate")
public class CerificateController {

	@Autowired
	private CertificateService certService;
	
	@Autowired
	private CommunicationService commService;
	
	private static final Logger logger = LoggerFactory.getLogger(CerificateController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Certificate>> getAllTakenTests() {
		List<Certificate> certs = certService.findAll();
		return new ResponseEntity<List<Certificate>>(certs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody CertificateGenerateDTO request) {
        logger.info("Generating certificate at time {}.", Calendar.getInstance().getTime());
        certService.setServer(request.getServer());
        PrivateKeyNCertificate[] chain;
        if (request.getType() == CertificateType.ROOT) {
        	certService.createRoot(new X500Principal(request.getX500Name()));
            return new ResponseEntity<>("Certificate successfully created!", HttpStatus.OK);
        } else if (request.getType() == CertificateType.MIDDLEMAN) {
            chain = certService.createNewSigned(new X500Principal(request.getX500Name()),
                    request.getIssuer().getSerialNumber(), request.getType());
            if (request.getDestination() != null && !request.getDestination().equals("")) {
            	commService.sendCertificate(chain, request.getDestination(), false);
            }
        } else {
            chain = certService.createNewSigned(new X500Principal(request.getX500Name()),
                    request.getIssuer().getSerialNumber(), request.getType());
            commService.sendCertificate(chain, "users", true);
        }

        return new ResponseEntity<>("Certificate successfully created!", HttpStatus.OK);
    }

}
