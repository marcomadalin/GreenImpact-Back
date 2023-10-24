package com.greenimpact.server.organization;

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

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public OrganizationEntity(String name) {
        this.name = name;
        this.roles = new ArrayList<>();
    }

    public OrganizationEntity(OrganizationDTO organization) {
        this.name = organization.getName();
        this.roles = new ArrayList<>();
    }

    public OrganizationDTO toDTO() {
        return new OrganizationDTO(id, name, roles.stream().map(role ->
                new UserRoleDTO(role.getUser().toSimplifiedDTO(), role.getRole().toString())).collect(Collectors.toList()));
    }

    public OrganizationDTO toSimplifiedDTO() {
        return new OrganizationDTO(id, name, null);
    }
}
