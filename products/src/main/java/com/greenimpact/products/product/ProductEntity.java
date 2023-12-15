package com.greenimpact.products.product;

import com.greenimpact.products.license.LicenseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Products")
public class ProductEntity {
    @ManyToMany(mappedBy = "products")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<LicenseEntity> licenses;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean enabled;
    @ManyToOne
    @JoinColumn(name = "parentId")
    private ProductEntity parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<ProductEntity> children;


    public ProductEntity(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        this.parent = null;
        this.children = new HashSet<>(List.of());
    }

    public ProductEntity(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.enabled = productDTO.getEnabled();
        this.parent = null;
        this.children = new HashSet<>(List.of());
    }

    public ProductEntity(String name, Boolean enabled, ProductEntity parent) {
        this.name = name;
        this.enabled = enabled;
        this.parent = parent;
        this.children = new HashSet<>(List.of());
    }

    public ProductDTO toDTO() {
        ProductDTO result = new ProductDTO(id, name, enabled, null, null);
        if (parent != null) result.setParent(new ProductDTO(parent.id, parent.name, enabled, null, null));
        if (children != null && !children.isEmpty()) {
            result.setChildren(new ArrayList<>(children.stream().map(ProductEntity::recursiveToDTO).collect(Collectors.toList())));
        }
        return result;
    }

    ProductDTO recursiveToDTO() {
        ProductDTO result = new ProductDTO(id, name, enabled, null, null);
        if (children != null && !children.isEmpty()) {
            result.setChildren(new ArrayList<>(children.stream().map(ProductEntity::recursiveToDTO).collect(Collectors.toList())));
        }
        return result;
    }
}
