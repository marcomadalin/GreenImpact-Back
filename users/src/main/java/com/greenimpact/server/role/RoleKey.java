package com.greenimpact.server.role;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class RoleKey implements Serializable {

    @Column(name = "userId")
    Long userId;

    @Column(name = "organizationId")
    Long organizationId;

}