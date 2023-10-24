package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String name;

    private int age;

    private OrganizationDTO loggedOrganization;

    private String role;
}
