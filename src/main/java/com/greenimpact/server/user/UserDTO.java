package com.greenimpact.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    public Long id;

    public String name;

    public int age;
}
