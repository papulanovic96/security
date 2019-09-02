package com.megatravel.service;

import java.util.ArrayList;

import com.megatravel.entity.Authority;

public interface AuthorityService {

    Authority findById(long id);

    ArrayList<Authority> findAll();
}
