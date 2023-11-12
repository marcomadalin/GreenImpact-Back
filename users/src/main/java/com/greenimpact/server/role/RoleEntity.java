package com.greenimpact.server.role;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Roles")
public class RoleEntity {

    @EmbeddedId
    RoleKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    UserEntity user;

    @ManyToOne
    @MapsId("organizationId")
    @JoinColumn(name = "organizationId")
    OrganizationEntity organization;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    public RoleEntity(UserEntity user, OrganizationEntity organization, RoleEnum role) {
        this.id = new RoleKey(user.getId(), organization.getId());
        this.user = user;
        this.organization = organization;
        this.role = role;
    }


}
