package com.greenimpact.server.organization;

import com.greenimpact.server.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganizationDTO>> getOrganizations() {
        return ResponseEntity.ok().body(organizationService.getAllOrganizations());
    }

    @GetMapping("/{organizationId}/allUsers")
    public ResponseEntity<List<UserDTO>> getOrganizationUsers(@PathVariable Long organizationId) throws Exception {
        List<UserDTO> result = organizationService.getOrganizationUsers(organizationId);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) throws Exception {
        OrganizationDTO result = organizationService.getOrganization(id);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/new")
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organization) {
        return ResponseEntity.ok().body(organizationService.createOrganization(organization));
    }

    @PostMapping("/{organizationId}/addUser")
    public ResponseEntity<OrganizationDTO> addUser(@PathVariable Long organizationId, @RequestParam Long userId,
                                                   @RequestParam String role) throws Exception {
        OrganizationDTO result = organizationService.addUser(organizationId, userId, role);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Long id,
                                                              @RequestBody OrganizationDTO organization) throws Exception {
        OrganizationDTO result = organizationService.updateOrganization(id, organization);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long id) {

        organizationService.deleteOrganization(id);
        return ResponseEntity.ok()
                .body("Organization deleted");
    }

    @DeleteMapping("/{organizationId}/removeUser")
    public ResponseEntity<OrganizationDTO> removeUser(@PathVariable Long organizationId,
                                                      @RequestParam Long userId) throws Exception {
        OrganizationDTO result = organizationService.removeUser(organizationId, userId);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }
}
