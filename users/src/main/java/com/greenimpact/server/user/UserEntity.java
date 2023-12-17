package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.role.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    private String locale;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean enabled;

    @JoinColumn(name = "lastLoggedOrgId", nullable = false)
    @ManyToOne
    private OrganizationEntity loggedOrganization;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public UserEntity(String username, String password, String name, String surname, String locale, String phoneNumber, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.locale = locale;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.loggedOrganization = null;
        this.roles = new ArrayList<>();
    }

    public UserEntity(UserDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.locale = user.getLocale();
        this.phoneNumber = user.getPhoneNumber();
        this.enabled = user.getEnabled();
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
        RoleEntity foundRole = null;
        Optional<RoleEntity> opt = roles.stream()
                .filter(role -> role.getOrganization().getId().equals(loggedOrganization.getId()))
                .findFirst();
        if (opt.isPresent()) foundRole = opt.get();

        return new UserDTO(id, username, password, name, surname, locale, phoneNumber, enabled, loggedOrganization.toSimplifiedDTO(),
                foundRole != null ? foundRole.getRole().toString() : "", foundRole != null ? foundRole.getMembershipDate() : LocalDate.now());
    }

    public UserDTO toSimplifiedDTO() {
        return new UserDTO(id, username, password, name, surname, locale, phoneNumber, enabled, null, null, null);
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", locale=" + locale + '\'' +
                ", phoneNumber=" + phoneNumber + '\'' +
                ", enabled=" + enabled + '\'' +
                ", loggedOrganization=" + loggedOrganization.toSimplifiedDTO().toString() + '\'' +
                ", role=" + roles.stream()
                .filter(role -> role.getOrganization().getId().equals(loggedOrganization.getId()))
                .findFirst()
                .map(foundRole -> foundRole.getRole().toString())
                .orElse("") + '\'' +
                '}';
    }
}
