package com.megatravel.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.megatravel.entityDTO.ResponseRole;
import com.megatravel.entityDTO.ResponseUser;

@Service
public class UserDetailsImplementation implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String uri = "https://localhost:8443/main-backend/users/find/username=" + username;
    	
        RestTemplate restTemplate = new RestTemplate();
        ResponseUser user = restTemplate.getForEntity(uri, ResponseUser.class).getBody();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (ResponseRole role : user.getRoles()){
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
		}

        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
			
	}


	

}
