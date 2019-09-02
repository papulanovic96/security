package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import repository.UserRepository;
import service.AuthService;
import entityDTO.LoginDTO;
import entityDTO.LoginResponseDTO;
import entityDTO.UserDTO;
import entity.UserRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


   
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        String token = authService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());

        Long userId = authService.getAuthUser().getId();
        return new ResponseEntity<>(new LoginResponseDTO(userId, token), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody UserRequest request) {
    	
    	User user = new User();
    	
    	user.setEmail(request.getEmail());
    	user.setFirstName(request.getFirstName());
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	user.setLastName(request.getLastName());
    	
    	
    	userRepository.save(user);
        
    }


}