package com.greenimpact.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String name;

    private int age;

    private List<Pair<String, String>> roles;
}
