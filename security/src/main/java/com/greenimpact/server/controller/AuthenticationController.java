package com.greenimpact.server.controller;

import com.greenimpact.server.model.LoginRequest;
import com.greenimpact.server.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    public AuthenticationController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDetails> logUser(@RequestBody LoginRequest loginRequest) {
        UserDetails result = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }
}
