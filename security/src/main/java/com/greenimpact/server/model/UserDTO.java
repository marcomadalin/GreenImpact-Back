package com.greenimpact.server.model;

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
