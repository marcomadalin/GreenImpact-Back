package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.role.RoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @JoinColumn(name = "lastLoggedOrgId", nullable = false)
    @OneToOne
    private OrganizationEntity loggedOrganization;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public UserEntity(String username, String password, String name, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.loggedOrganization = null;
        this.roles = new ArrayList<>();
    }

    public UserEntity(UserDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.age = user.getAge();
        this.loggedOrganization = null;
        this.roles = new ArrayList<>();
    }

    public String getOrganizationRole(Long organizationId) {
        return roles.stream().filter(role -> role.getOrganization().getId().equals(organizationId)).findFirst().get().getRole().toString();
    }


    public UserDTO toDTO() {
        return new UserDTO(id, username, password, name, age, loggedOrganization.toSimplifiedDTO(),
                roles.stream().filter(role -> role.getOrganization().getId().equals(loggedOrganization.getId())).findFirst().get().getRole().toString());
    }

    public UserDTO toSimplifiedDTO() {
        return new UserDTO(id, username, password, name, age, null, null);
    }
}
