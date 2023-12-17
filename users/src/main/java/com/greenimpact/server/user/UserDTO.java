package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String locale;

    private String phoneNumber;

    private Boolean enabled;

    private OrganizationDTO loggedOrganization;

    private String role;

    private LocalDate membershipDate;
}
