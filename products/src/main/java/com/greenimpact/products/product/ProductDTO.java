package com.greenimpact.products.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Long id;

    private String name;

    private ProductDTO parent;

    private List<ProductDTO> children;
}
