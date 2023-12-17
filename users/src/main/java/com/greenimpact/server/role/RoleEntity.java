package com.greenimpact.server.role;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column
    private LocalDate membershipDate;

    public RoleEntity(UserEntity user, OrganizationEntity organization, RoleEnum role, LocalDate membershipDate) {
        this.id = new RoleKey(user.getId(), organization.getId());
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.membershipDate = membershipDate;
    }


}
