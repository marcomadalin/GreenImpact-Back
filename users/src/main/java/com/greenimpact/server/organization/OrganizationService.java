package com.greenimpact.server.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAll().stream().map(OrganizationEntity::toDTO).collect(Collectors.toList());
    }

    public OrganizationDTO getOrganization(Long id) {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(id);

        return organizationOpt.map(OrganizationEntity::toDTO).orElse(null);
    }

    public OrganizationDTO createOrganization(OrganizationDTO organization) {
        return organizationRepository.save(new OrganizationEntity(organization)).toDTO();
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organization) {
        Optional<OrganizationEntity> userOpt = organizationRepository.findById(id);

        if (userOpt.isPresent()) {
            OrganizationEntity act = userOpt.get();

            act.setName(organization.name);
            return organizationRepository.save(act).toDTO();
        }

        return null;
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}
