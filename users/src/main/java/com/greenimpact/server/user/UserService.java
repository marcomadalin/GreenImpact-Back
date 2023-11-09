package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationDTO;
import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.organization.OrganizationRepository;
import com.greenimpact.server.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final OrganizationService organizationService;

    @Autowired
    public UserService(UserRepository userRepository, OrganizationService organizationService, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationDTO> getAllUserOrganizations(Long id) throws Exception {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) return userOpt.get().getRoles().stream()
                .map(roleEntity -> roleEntity.getOrganization().toSimplifiedDTO()).toList();
        else throw new Exception("USER DOES NOT EXIST");
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntity::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        return userOpt.map(UserEntity::toDTO).orElse(null);
    }

    public UserDTO getUser(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        return userOpt.map(UserEntity::toDTO).orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(userDTO.getLoggedOrganization().getId());
        if (organizationOpt.isPresent()) {
            UserEntity user = new UserEntity(userDTO);
            user.setLoggedOrganization(organizationOpt.get());
            user = userRepository.save(user);
            organizationService.addUser(organizationOpt.get().getId(), user.getId(), userDTO.getRole());
            UserDTO result = userRepository.findById(user.getId()).get().toDTO();
            result.setRole(userDTO.getRole());
            return result;
        }
        return null;
    }

    public UserDTO updateUser(Long id, UserDTO user) {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserEntity act = userOpt.get();

            user.setUsername(act.getUsername());
            user.setPassword(act.getPassword());
            act.setName(user.getName());
            act.setAge(user.getAge());
            return userRepository.save(act).toDTO();
        }

        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
