package service;

import entity.User;

public interface AuthService {

    String authenticate(String email, String password);

    User getAuthUser();
}