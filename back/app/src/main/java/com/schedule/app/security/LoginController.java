package com.schedule.app.security;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/api/login")
    public  void login(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                                        loginRequest.userId(),
                                                        loginRequest.password());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
