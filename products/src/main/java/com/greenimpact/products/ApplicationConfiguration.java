package com.greenimpact.products;

import com.greenimpact.products.product.ProductEntity;
import com.greenimpact.products.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            ProductEntity parent = new ProductEntity("BASE");
            parent = productRepository.save(parent);

            ProductEntity child1 = new ProductEntity("INDICATOR", parent, null);
            child1 = productRepository.save(child1);

            ProductEntity child2 = new ProductEntity("PLANS", parent, null);
            child2 = productRepository.save(child2);

            ProductEntity grandchild = new ProductEntity("AREA", child2, null);
            grandchild = productRepository.save(grandchild);
        };
    }
}
