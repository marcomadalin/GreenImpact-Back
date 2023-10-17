package com.greenimpact.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntity::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        return userOpt.map(UserEntity::toDTO).orElse(null);
    }

    public UserDTO createUser(UserDTO user) {
        return userRepository.save(new UserEntity(user)).toDTO();
    }

    public UserDTO updateUser(Long id, UserDTO user) {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserEntity act = userOpt.get();

            act.setName(user.name);
            act.setAge(user.age);
            return userRepository.save(act).toDTO();
        }

        return null;
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }
}
