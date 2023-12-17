package com.greenimpact.server.role;

import com.greenimpact.server.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserRoleDTO {
    private UserDTO user;
    private String Role;
    private LocalDate membershipDate;
}