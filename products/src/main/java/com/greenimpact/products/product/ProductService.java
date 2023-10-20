package com.greenimpact.products.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductEntity::toDTO).collect(Collectors.toList());
    }

    public ProductDTO getProduct(Long id) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        return productOpt.map(ProductEntity::toDTO).orElse(null);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        return productRepository.save(new ProductEntity(productDTO)).toDTO();
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return null;

        ProductEntity act = productOpt.get();
        act.setName(productDTO.getName());

        Optional<ProductEntity> parentOpt;
        if (productDTO.getParent() != null) {
            parentOpt = productRepository.findById(productDTO.getParent().getId());
            if (parentOpt.isEmpty()) return null;
            act.setParent(productOpt.get());
        } else act.setParent(null);


        act.getChildren().forEach(child -> {
            if (Objects.equals(child.getParent().getId(), act.getId())) {
                child.setParent(null);
                productRepository.save(child);
            }
        });

        if (productDTO.getChildren() != null) {
            act.getChildren().forEach(child -> {
                Optional<ProductEntity> childOpt = productRepository.findById(child.getId());
                if (childOpt.isPresent()) {
                    ProductEntity childProduct = childOpt.get();
                    childProduct.setParent(act);
                    productRepository.save(childProduct);
                }
            });
        } else act.setChildren(new HashSet<>(List.of()));

        return productRepository.save(act).toDTO();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
