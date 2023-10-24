package com.greenimpact.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO {
    private UserDTO user;
    private String Role;
}