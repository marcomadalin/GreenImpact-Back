package com.greenimpact.products.product;

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

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Products")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private ProductEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<ProductEntity> children;

    public ProductEntity(String name) {
        this.name = name;
        this.parent = null;
        this.children = null;
    }

    public ProductEntity(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.parent = null;
        this.children = null;
    }

    public ProductEntity(String name, ProductEntity parent, Set<ProductEntity> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    ProductDTO toDTO() {
        ProductDTO result = new ProductDTO(id, name, null, null);
        if (parent != null) result.setParent(new ProductDTO(parent.id, parent.name, null, null));
        if (children != null && !children.isEmpty()) {
            result.setChildren(new ArrayList<>(children.stream().map(ProductEntity::recrusiveToDTO).collect(Collectors.toList())));
        }
        return result;
    }

    ProductDTO recrusiveToDTO() {
        ProductDTO result = new ProductDTO(id, name, null, null);
        if (children != null && !children.isEmpty()) {
            result.setChildren(new ArrayList<>(children.stream().map(ProductEntity::recrusiveToDTO).collect(Collectors.toList())));
        }
        return result;
    }


}
