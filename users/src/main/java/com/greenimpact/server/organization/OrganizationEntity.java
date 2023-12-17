package com.greenimpact.server.organization;

import com.greenimpact.server.role.RoleEntity;
import com.greenimpact.server.role.UserRoleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Organizations")
public class OrganizationEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Boolean enabled;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public OrganizationEntity(Boolean enabled, OrganizationType type, String name) {
        this.enabled = enabled;
        this.type = type;
        this.name = name;
        this.roles = new ArrayList<>();
    }

    public OrganizationEntity(OrganizationDTO organization) {
        this.enabled = organization.getEnabled();
        this.type = OrganizationType.valueOf(organization.getType());
        this.name = organization.getName();
        this.roles = new ArrayList<>();
    }

    public OrganizationDTO toDTO() {
        return new OrganizationDTO(id, enabled, type.toString(), name, roles.stream().map(role ->
                new UserRoleDTO(role.getUser().toSimplifiedDTO(), role.getRole().toString(), role.getMembershipDate())).collect(Collectors.toList()));
    }

    public OrganizationDTO toSimplifiedDTO() {
        return new OrganizationDTO(id, enabled, type.toString(), name, null);
    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "id=" + id +
                ", enabled='" + enabled + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles.stream().map(role ->
                new UserRoleDTO(role.getUser().toSimplifiedDTO(), role.getRole().toString(), role.getMembershipDate())).toList() + '\'' +
                '}';
    }
}
