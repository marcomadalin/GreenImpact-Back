package com.greenimpact.products.license;

import com.greenimpact.products.product.ProductDTO;
import com.greenimpact.products.product.ProductEntity;
import com.greenimpact.products.product.ProductRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ProductRepository productRepository;

    public LicenseService(LicenseRepository licenseRepository, ProductRepository productRepository) {
        this.licenseRepository = licenseRepository;
        this.productRepository = productRepository;
    }

    public List<LicenseDTO> getAllLicenses(Long organizationId) {
        return licenseRepository.findByOrganizationId(organizationId).stream().map(LicenseEntity::toDTO).collect(Collectors.toList());
    }

    public LicenseDTO getLicense(Long id) {
        Optional<LicenseEntity> licenseOpt = licenseRepository.findById(id);
        if (licenseOpt.isEmpty()) throw new RuntimeException("LICENSE DOES NOT EXIST");
        return licenseOpt.map(LicenseEntity::toDTO).orElse(null);
    }

    public LicenseDTO createLicense(LicenseDTO licenseDTO) throws Exception {
        licenseDTO.setKey(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
        LicenseEntity license = new LicenseEntity(licenseDTO);
        List<ProductEntity> products = productRepository.findAllById(licenseDTO.getProducts().stream().map(ProductDTO::getId).collect(Collectors.toSet()));
        if (products.size() != licenseDTO.getProducts().size()) throw new Exception("PRODUCT DOES NOT EXIST");
        license.setProducts(products);
        return licenseRepository.save(new LicenseEntity(licenseDTO)).toDTO();
    }

    public LicenseDTO updateLicense(Long id, LicenseDTO licenseDTO) {
        Optional<LicenseEntity> licenseOpt = licenseRepository.findById(id);
        if (licenseOpt.isEmpty()) throw new RuntimeException("LICENSE DOES NOT EXIST");

        LicenseEntity act = licenseOpt.get();
        act.setEnabled(licenseDTO.getEnabled());
        act.setActive(licenseDTO.getActive());
        act.setStartDate(licenseDTO.getStartDate());
        act.setEndDate(licenseDTO.getEndDate());

        return licenseRepository.save(act).toDTO();
    }

    public void deleteLicense(Long id) {
        licenseRepository.deleteById(id);
    }

}
