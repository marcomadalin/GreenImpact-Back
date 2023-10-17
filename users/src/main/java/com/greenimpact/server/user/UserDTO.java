package com.greenimpact.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    public Long id;

    public String name;

    public int age;

    public List<Pair<String, String>> roles;
}
