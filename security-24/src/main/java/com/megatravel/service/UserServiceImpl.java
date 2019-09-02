package com.megatravel.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.megatravel.entity.Authority;
import com.megatravel.entity.User;
import com.megatravel.entity.UserAuthority;
import com.megatravel.entityDTO.UserPasswordDTO;
import com.megatravel.entityDTO.UserRegistrationDTO;
import com.megatravel.entityDTO.UserUpdateDTO;
import com.megatravel.exception.AccessDeniedException;
import com.megatravel.exception.BadRegistrationParametersException;
import com.megatravel.exception.EntityNotFoundException;
import com.megatravel.repository.UserRepository;
import com.megatravel.service.AuthService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;



    @Override
    public User findById(long id) {
        Optional<User> opt = this.userRepository.findById(id);

        return opt.orElseThrow(() -> new EntityNotFoundException(User.class, "id", Long.toString(id)));
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> opt = this.userRepository.findByEmail(email);

        return opt.orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));
    }

    @Override
    public User getUserById(long id) {
        User authUser = authService.getAuthUser();

        if (authUser.getId() != id) {
            throw new AccessDeniedException();
        }

        return authUser;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> allUsers = (ArrayList<User>)userRepository.findAll();

        return allUsers;

    }



    @Override
    @Transactional
    public User create(UserRegistrationDTO userRegistrationDTO) {
        Optional<User> user = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (user.isPresent()) {
            throw new BadRegistrationParametersException("User with given email is already registered.");
        }

        User newUser = new User();
        newUser.setFirstName(userRegistrationDTO.getFirstName());
        newUser.setLastName(userRegistrationDTO.getLastName());
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        User createdUser = userRepository.save(newUser);


        Authority authority = authorityService.findById(userRegistrationDTO.getAuthorityId());

        UserAuthority ua = new UserAuthority();
        ua.setAuthority(authority);
        ua.setUser(createdUser);

        createdUser.getUserAuthorities().add(ua);
        userRepository.save(createdUser);

        return createdUser;
    }

    @Override
    @Transactional
    public User update(long userId, UserUpdateDTO userUpdateDTO) {
        User authUser = authService.getAuthUser();

        if (authUser.getId() != userId) {
            throw new AccessDeniedException();
        }

        User updateUser = findById(userId);
        Optional<User> userWithGivenEmail = userRepository.findByEmail(userUpdateDTO.getEmail());

        if (userWithGivenEmail.isPresent() && userWithGivenEmail.get().getId() != updateUser.getId()) {
            throw new BadRegistrationParametersException("User with given email is already registered.");
        }

        updateUser.setFirstName(userUpdateDTO.getFirstName());
        updateUser.setLastName(userUpdateDTO.getLastName());
        updateUser.setEmail(userUpdateDTO.getEmail());

        User saved = userRepository.save(updateUser);
        return saved;
    }

    @Override
    public User changePassword(long userId, UserPasswordDTO userPasswordDTO) {
        User authUser = authService.getAuthUser();
        if (authUser.getId() != userId) {
            throw new AccessDeniedException();
        }

        User updateUser = findById(userId);

        if (!passwordEncoder.matches(userPasswordDTO.getOldPassword(), updateUser.getPassword())) {
            throw new AccessDeniedException();
        }

        updateUser.setPassword(passwordEncoder.encode(userPasswordDTO.getNewPassword()));

        User saved = userRepository.save(updateUser);
        return saved;
    }
}
