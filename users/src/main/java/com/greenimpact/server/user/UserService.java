package com.greenimpact.server.user;

import com.greenimpact.server.organization.OrganizationDTO;
import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.organization.OrganizationRepository;
import com.greenimpact.server.organization.OrganizationService;
import com.greenimpact.server.security.JwtService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final OrganizationService organizationService;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, OrganizationService organizationService, OrganizationRepository organizationRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get();
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
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user = userRepository.save(user);
            organizationService.addUser(organizationOpt.get().getId(), user.getId(), userDTO.getRole());
            UserDTO result = userRepository.findById(user.getId()).get().toDTO();
            result.setRole(userDTO.getRole());
            return result;
        }
        return null;
    }

    public String changeOrganization(Long organizationId, String username) throws Exception {
        Optional<OrganizationEntity> organizationOpt = organizationRepository.findById(organizationId);
        if (organizationOpt.isEmpty()) throw new Exception("ORGANIZATION DOES NOT EXIST");

        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        UserEntity user = userOpt.get();
        user.setLoggedOrganization(organizationOpt.get());
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public UserDTO updateUser(Long id, UserDTO user) throws Exception {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserEntity act = userOpt.get();
            user.setUsername(act.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(act.getPassword()));
            act.setName(user.getName());
            act.setSurname(user.getSurname());
            act.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(act).toDTO();
        }
        else throw new Exception("USER DOES NOT EXIST");
    }

    public UserDTO updateLocale(Long id, String locale) throws Exception {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserEntity act = userOpt.get();
            act.setLocale(locale);
            return userRepository.save(act).toDTO();
        }
        else throw new Exception("USER DOES NOT EXIST");
    }

    public UserDTO changePassword(Long id, String oldPassword, String newPassword) throws Exception {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserEntity act = userOpt.get();
            if (!new BCryptPasswordEncoder().matches(oldPassword, act.getPassword())) throw new Exception("INCORRECT PASSWORD");
            act.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            return userRepository.save(act).toDTO();
        }
        else throw new Exception("USER DOES NOT EXIST");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
