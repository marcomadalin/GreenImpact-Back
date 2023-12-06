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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class UserEntity implements UserDetails {
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
    private String surname;

    @Column(nullable = false)
    private int age;

    @JoinColumn(name = "lastLoggedOrgId", nullable = false)
    @ManyToOne
    private OrganizationEntity loggedOrganization;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public UserEntity(String username, String password, String name, String surname, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.loggedOrganization = null;
        this.roles = new ArrayList<>();
    }

    public UserEntity(UserDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.age = user.getAge();
        this.loggedOrganization = null;
        this.roles = new ArrayList<>();
    }

    public String getOrganizationRole(Long organizationId) {
        return roles.stream().filter(role -> role.getOrganization().getId().equals(organizationId)).findFirst().get().getRole().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getOrganizationRole(loggedOrganization.getId())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDTO toDTO() {
        return new UserDTO(id, username, password, name, surname, age, loggedOrganization.toSimplifiedDTO(),
                roles.stream()
                        .filter(role -> role.getOrganization().getId().equals(loggedOrganization.getId()))
                        .findFirst()
                        .map(foundRole -> foundRole.getRole().toString())
                        .orElse(""));
    }

    public UserDTO toSimplifiedDTO() {
        return new UserDTO(id, username, password, name, surname, age, null, null);
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age + '\'' +
                ", loggedOrganization=" + loggedOrganization.toSimplifiedDTO().toString() + '\'' +
                ", role=" + roles.stream()
                .filter(role -> role.getOrganization().getId().equals(loggedOrganization.getId()))
                .findFirst()
                .map(foundRole -> foundRole.getRole().toString())
                .orElse("") + '\'' +
                '}';
    }
}
