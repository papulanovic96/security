package com.megatravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.megatravel.entity.User;
import com.megatravel.entityDTO.UserDTO;
import com.megatravel.entityDTO.UserPasswordDTO;
import com.megatravel.entityDTO.UserRegistrationDTO;
import com.megatravel.entityDTO.UserUpdateDTO;
import com.megatravel.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyAuthority('admin', 'regular')")
    @RequestMapping(value = "/{id}",
		            method = RequestMethod.GET,
		            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

	
	  @PreAuthorize("hasAnyAuthority('student_service')")
	  
	  @RequestMapping(value = "/tutors", method = RequestMethod.POST, produces =
	  MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<UserDTO>
	  createTutor(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
	  
	  // Not good way to sort this out.. 
	  Long TUTOR_AUTHORITY_ID = 2L;
	  userRegistrationDTO.setAuthorityId(TUTOR_AUTHORITY_ID);
	  
	  User user = userService.create(userRegistrationDTO); return new
	  ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED); }
	  
	  
	  @RequestMapping(value = "/students", method = RequestMethod.POST, produces =
	  MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<UserDTO>
	  createStudent(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
	  
	  // Not good way to sort this out.. 
		  
	  Long STUDENT_AUTHORITY_ID = 3L;
	  userRegistrationDTO.setAuthorityId(STUDENT_AUTHORITY_ID);
	  
	  User user = userService.create(userRegistrationDTO); return new
	  ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED); }
	  
	  @PreAuthorize("hasAnyAuthority('tutor', 'student')")
	  
	  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces =
	  MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<UserDTO>
	  update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO
	  userUpdateDTO) {
	  
	  User user = userService.update(id, userUpdateDTO); return new
	  ResponseEntity<>(new UserDTO(user), HttpStatus.OK); }
	  
	  @PreAuthorize("hasAnyAuthority('tutor', 'student')")
	  
	  @RequestMapping(value = "/{id}/password", method = RequestMethod.PUT,
	  produces = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<UserDTO>
	  changePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDTO
	  userPasswordDTO) {
	  
	  User user = userService.changePassword(id, userPasswordDTO); return new
	  ResponseEntity<>(new UserDTO(user), HttpStatus.OK); }
	 

}
