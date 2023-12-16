package com.greenimpact.products.license;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/{organizationId}/all")
    public ResponseEntity<List<LicenseDTO>> getLicenses(@PathVariable Long organizationId) {
        return ResponseEntity.ok().body(licenseService.getAllLicenses(organizationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDTO> getLicense(@PathVariable Long id) {
        LicenseDTO result = licenseService.getLicense(id);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/new")
    public ResponseEntity<LicenseDTO> createLicense(@RequestBody LicenseDTO licenseDTO) throws Exception {
        return ResponseEntity.ok().body(licenseService.createLicense(licenseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseDTO> updateLicense(@PathVariable Long id, @RequestBody LicenseDTO licenseDTO) {
        LicenseDTO result = licenseService.updateLicense(id, licenseDTO);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        licenseService.deleteLicense(id);
        return ResponseEntity.ok()
                .body("License deleted");
    }
}
