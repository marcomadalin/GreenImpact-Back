package com.greenimpact.server.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private Long id;
    private Boolean enabled;
    private String type;
    private String name;
    private List<UserRoleDTO> users;
}
