package com.megatravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megatravel.entity.Authority;
import com.megatravel.exception.EntityNotFoundException;
import com.megatravel.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findById(long id) {
        Optional<Authority> authorities = authorityRepository.findById(id);

        return authorities.orElseThrow(() -> new EntityNotFoundException(Authority.class, "id", Long.toString(id)));
    }

    @Override
    public ArrayList<Authority> findAll() {
        ArrayList<Authority> listOfAuthorities = (ArrayList<Authority>)authorityRepository.findAllByOrderByIdAsc();

        if (listOfAuthorities == null) {
            throw new EntityNotFoundException(Authority.class, "all", "null");
        }

        return listOfAuthorities;
    }
}
