package com.greenimpact.products.license;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseEntity, Long> {
    List<LicenseEntity> findByOrganizationId(Long organizationId);
}
