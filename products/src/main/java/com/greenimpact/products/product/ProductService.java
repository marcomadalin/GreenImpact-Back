package com.greenimpact.products.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (productOpt.isPresent()) {
            ProductEntity act = productOpt.get();

            act.setName(productDTO.getName());
            return productRepository.save(act).toDTO();
        }

        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
