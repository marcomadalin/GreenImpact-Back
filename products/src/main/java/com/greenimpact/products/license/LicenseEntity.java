package com.greenimpact.products.license;

import com.greenimpact.products.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Licenses")
public class LicenseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long organizationId;

    private String key;

    @Column
    private Boolean active;

    @Column
    private Boolean enabled;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
            name = "License_Products",
            joinColumns = @JoinColumn(name = "licenseId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductEntity> products;

    public LicenseEntity(Long organizationId, String key, Boolean active, Boolean enabled, LocalDate startDate, LocalDate endDate) {
        this.organizationId = organizationId;
        this.key = key;
        this.active = active;
        this.enabled = enabled;
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = Collections.emptyList();
    }

    public LicenseEntity(LicenseDTO licenseDTO) {
        this.organizationId = licenseDTO.getOrganizationId();
        this.key = licenseDTO.getKey();
        this.active = licenseDTO.getActive();
        this.enabled = licenseDTO.getEnabled();
        this.startDate = licenseDTO.getStartDate();
        this.endDate = licenseDTO.getEndDate();
        this.products = Collections.emptyList();
    }

    LicenseDTO toDTO() {
        return new LicenseDTO(id, organizationId, key, active, enabled, startDate, endDate, products.stream().map(ProductEntity::toDTO).collect(Collectors.toList()));
    }

}
