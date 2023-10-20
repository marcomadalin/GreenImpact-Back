package com.greenimpact.server.organization;

import com.greenimpact.server.role.RoleEntity;
import com.greenimpact.server.role.RoleEnum;
import com.greenimpact.server.role.RoleKey;
import com.greenimpact.server.role.RoleRepository;
import com.greenimpact.server.user.UserEntity;
import com.greenimpact.server.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

    public OrganizationDTO addUser(Long organizationId, Long userId, String role) {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) return null;

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return null;

        RoleEntity result = roleRepository.save(new RoleEntity(userOpt.get(), organizationOpt.get(), RoleEnum.valueOf(role)));
        organizationOpt.get().getRoles().add(result);
        return organizationOpt.get().toDTO();
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organization) {
        Optional<OrganizationEntity> userOpt = organizationRepository.findById(id);

        if (userOpt.isPresent()) {
            OrganizationEntity act = userOpt.get();

            act.setName(organization.getName());
            return organizationRepository.save(act).toDTO();
        }

        return null;
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }


    @Transactional
    public OrganizationDTO removeUser(Long organizationId, Long userId) {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) return null;

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return null;

        roleRepository.deleteById(new RoleKey(userId, organizationId));
        return organizationRepository.findById(organizationId).get().toDTO();
    }
}
