package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationDTO;
import com.greenimpact.server.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}/myOrganizations")
    public ResponseEntity<List<OrganizationDTO>> getUserOrganizations(@PathVariable Long id) throws Exception {
        List<OrganizationDTO> result = userService.getAllUserOrganizations(id);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws Exception {
        UserDTO result = userService.getUser(id);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws Exception {
        UserDTO result = userService.getUser(username);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/changeOrganization")
    public ResponseEntity<String> changeOrganization(@RequestHeader HttpHeaders headers, @RequestParam Long organizationId) throws Exception {
        String token = Objects.requireNonNull(headers.get("authorization")).get(0).substring(7);
        String username = jwtService.extractUsername(token);

        return ResponseEntity.ok().body(userService.changeOrganization(organizationId, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) throws Exception {
        UserDTO result = userService.updateUser(id, user);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/changeLocale/{id}")
    public ResponseEntity<UserDTO> updateLocale(@PathVariable Long id, @RequestParam String locale) throws Exception {
        UserDTO result = userService.updateLocale(id, locale);
        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long id, @RequestParam String oldPassword,
                                                  @RequestParam String newPassword) throws Exception {
        UserDTO result = userService.changePassword(id, oldPassword, newPassword);
        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok()
                .body("User deleted");
    }
}
