package com.greenimpact.products.license;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public List<LicenseDTO> getAllLicenses(Long organizationId) {
        return licenseRepository.findByOrganizationId(organizationId).stream().map(LicenseEntity::toDTO).collect(Collectors.toList());
    }

    public LicenseDTO getLicense(Long id) {
        Optional<LicenseEntity> licenseOpt = licenseRepository.findById(id);
        if (licenseOpt.isEmpty()) throw new RuntimeException("LICENSE DOES NOT EXIST");
        return licenseOpt.map(LicenseEntity::toDTO).orElse(null);
    }

    public LicenseDTO createLicense(LicenseDTO licenseDTO) {
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
