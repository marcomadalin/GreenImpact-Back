package com.greenimpact.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrganizationDTO {

    private Long id;
    private String name;
    private List<UserRoleDTO> users;
}
