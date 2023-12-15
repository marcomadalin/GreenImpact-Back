package com.greenimpact.products.configuration;

import com.greenimpact.products.license.LicenseEntity;
import com.greenimpact.products.license.LicenseRepository;
import com.greenimpact.products.product.ProductEntity;
import com.greenimpact.products.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, LicenseRepository licenseRepository) {
        return args -> {
            ProductEntity parent = new ProductEntity("BASE", true);
            parent = productRepository.save(parent);

            ProductEntity child1 = new ProductEntity("INDICATOR", true, parent);
            child1 = productRepository.save(child1);

            ProductEntity child2 = new ProductEntity("PLANS", true, parent);
            child2 = productRepository.save(child2);

            ProductEntity grandchild = new ProductEntity("AREA", true, child2);
            grandchild = productRepository.save(grandchild);

            LicenseEntity license1 = new LicenseEntity(1L, new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()),
                    true, true, LocalDate.now().minusYears(2), LocalDate.now().plusYears(3));
            List<ProductEntity> products = new ArrayList<>();
            products.add(parent);
            license1.setProducts(products);
            licenseRepository.save(license1);

            LicenseEntity license2 = new LicenseEntity(1L, new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()),
                    false, true, LocalDate.now().minusYears(5), LocalDate.now().plusYears(2));
            license2.setProducts(products);
            licenseRepository.save(license2);
        };
    }
}
