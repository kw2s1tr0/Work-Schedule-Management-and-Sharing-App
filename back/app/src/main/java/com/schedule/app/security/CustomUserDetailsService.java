package com.schedule.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.schedule.app.repository.AuthUserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final AuthUserMapper authUserMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AuthUser authUser = authUserMapper.readAuthUser(username);
        if(authUser == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new CustomUserDetails(authUser);
    }
}