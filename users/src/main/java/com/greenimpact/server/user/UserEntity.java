package com.greenimpact.server.user;

import com.greenimpact.server.role.RoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleEntity> roles;

    public UserEntity(String name, int age) {
        this.name = name;
        this.age = age;
        this.roles = new ArrayList<>();
    }

    public UserEntity(UserDTO user) {
        this.name = user.name;
        this.age = user.age;
        this.roles = new ArrayList<>();
    }

    public UserDTO toDTO() {
        return new UserDTO(id, name, age, roles.stream().map(role ->
                Pair.of(role.getOrganization().getName(), role.getRole().toString())).collect(Collectors.toList()));
    }
}