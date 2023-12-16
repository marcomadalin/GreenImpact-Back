package com.greenimpact.server.security;

import com.greenimpact.server.user.UserDTO;
import com.greenimpact.server.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService, JwtService jwtService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @GetMapping("/whoami")
    public ResponseEntity<UserDTO> whoami(@RequestHeader HttpHeaders headers) throws Exception {
        UserDTO result = userService.getUser(jwtService.extractUsername(Objects.requireNonNull(headers.get("authorization")).get(0).substring(7)));

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(jwtService.isTokenValid(token, userService.loadUserByUsername(username)));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.login(loginRequest);

        if (token == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO user) throws Exception {
        UserDTO result = userService.createUser(user);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

}
