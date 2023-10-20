package com.greenimpact.server.user;

import com.greenimpact.server.role.RoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public UserEntity(String username, String password, String name, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.roles = new ArrayList<>();
    }

    public UserEntity(UserDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.age = user.getAge();
        this.roles = new ArrayList<>();
    }


    public UserDTO toDTO() {
        return new UserDTO(id, username, password, name, age, roles.stream().map(role ->
                Pair.of(role.getOrganization().getName(), role.getRole().toString())).collect(Collectors.toList()));
    }
}
