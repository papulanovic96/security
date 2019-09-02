package service;

import entity.Authority;

import java.util.ArrayList;

public interface AuthorityService {

    Authority findById(long id);

    ArrayList<Authority> findAll();
}
