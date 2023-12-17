package com.greenimpact.server.organization;

import com.greenimpact.server.role.RoleEntity;
import com.greenimpact.server.role.RoleEnum;
import com.greenimpact.server.role.RoleKey;
import com.greenimpact.server.role.RoleRepository;
import com.greenimpact.server.user.UserDTO;
import com.greenimpact.server.user.UserEntity;
import com.greenimpact.server.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<UserDTO> getOrganizationUsers(Long organizationId) throws Exception {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) throw new Exception("ORGANIZATION DOES NOT EXISTS");

        List<UserEntity> users = userRepository.findAllById(organizationOpt.get().getRoles().stream()
                .map(role -> role.getUser().getId()).collect(Collectors.toList()));
        return users.stream().map(UserEntity::toDTO).collect(Collectors.toList());
    }

    public OrganizationDTO getOrganization(Long id) throws Exception {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(id);
        if (organizationOpt.isEmpty()) throw new Exception("ORGANIZATION DOES NOT EXISTS");

        return organizationOpt.get().toDTO();
    }

    public OrganizationDTO createOrganization(OrganizationDTO organization) {
        return organizationRepository.save(new OrganizationEntity(organization)).toDTO();
    }

    public OrganizationDTO addUser(Long organizationId, Long userId, String role) throws Exception {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) throw new Exception("ORGANIZATION DOES NOT EXISTS");

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new Exception("USER DOES NOT EXISTS");

        RoleEntity result = roleRepository.save(new RoleEntity(userOpt.get(), organizationOpt.get(), RoleEnum.valueOf(role), LocalDate.now()));
        organizationOpt.get().getRoles().add(result);
        return organizationOpt.get().toDTO();
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organization) throws Exception {
        Optional<OrganizationEntity> userOpt = organizationRepository.findById(id);

        if (userOpt.isPresent()) {
            OrganizationEntity act = userOpt.get();
            act.setEnabled(organization.getEnabled());
            act.setName(organization.getName());
            return organizationRepository.save(act).toDTO();
        } else throw new Exception("ORGANIZATION DOES NOT EXISTS");
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }


    @Transactional
    public OrganizationDTO removeUser(Long organizationId, Long userId) throws Exception {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) throw new Exception("ORGANIZATION DOES NOT EXISTS");

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new Exception("USER DOES NOT EXISTS");

        if (userOpt.get().getRoles().size() == 1) userRepository.deleteById(userOpt.get().getId());
        else roleRepository.deleteById(new RoleKey(userId, organizationId));
        return organizationRepository.findById(organizationId).get().toDTO();
    }

}
