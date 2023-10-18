package com.greenimpact.server.organization;

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
@Table(name = "Organizations")
public class OrganizationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<RoleEntity> roles;

    public OrganizationEntity(String name) {
        this.name = name;
        this.roles = new ArrayList<>();
    }

    public OrganizationEntity(OrganizationDTO organization) {
        this.name = organization.name;
        this.roles = new ArrayList<>();
    }

    public OrganizationDTO toDTO() {
        return new OrganizationDTO(id, name, roles.stream().map(role ->
                Pair.of(role.getUser().getName(), role.getRole().toString())).collect(Collectors.toList()));
    }
}
