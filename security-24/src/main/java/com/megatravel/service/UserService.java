package com.megatravel.service;


import java.util.ArrayList;

import com.megatravel.entity.User;
import com.megatravel.entityDTO.UserPasswordDTO;
import com.megatravel.entityDTO.UserRegistrationDTO;
import com.megatravel.entityDTO.UserUpdateDTO;


public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User getUserById(long id);

    ArrayList<User> findAll();

    User create(UserRegistrationDTO userRegistrationDTO);

    User update(long id, UserUpdateDTO userUpdateDTO);

    User changePassword(long id, UserPasswordDTO userPasswordDTO);
}
