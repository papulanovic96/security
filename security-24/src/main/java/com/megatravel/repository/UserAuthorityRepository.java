package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.entity.UserAuthority;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}