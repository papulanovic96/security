package service;


import entity.User;
import entityDTO.UserPasswordDTO;
import entityDTO.UserRegistrationDTO;
import entityDTO.UserUpdateDTO;

import java.util.ArrayList;


public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User getUserById(long id);

    ArrayList<User> findAll();

    User create(UserRegistrationDTO userRegistrationDTO);

    User update(long id, UserUpdateDTO userUpdateDTO);

    User changePassword(long id, UserPasswordDTO userPasswordDTO);
}
