package com.greenimpact.server.organization;

import com.greenimpact.server.role.UserRoleDTO;
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
