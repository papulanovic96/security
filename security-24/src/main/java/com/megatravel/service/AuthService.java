package com.megatravel.service;

import com.megatravel.entity.User;

public interface AuthService {

    String authenticate(String email, String password);

    User getAuthUser();
}