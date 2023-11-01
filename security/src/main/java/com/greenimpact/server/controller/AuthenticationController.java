package com.greenimpact.server.controller;

import com.greenimpact.server.model.LoginRequest;
import com.greenimpact.server.model.UserDTO;
import com.greenimpact.server.service.AuthenticationService;
import com.greenimpact.server.service.JwtService;
import com.greenimpact.server.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    public AuthenticationController(UserDetailsService userDetailsService, AuthenticationService authenticationService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        UserDTO result = authenticationService.signup(userDTO);
        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.login(loginRequest);

        if (token == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(jwtService.isTokenValid(token, userDetailsService.loadUserByUsername(username)));
    }
}
