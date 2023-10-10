package com.greenimpact.server.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User Marco = new User("Marco", 23);
            User Karina = new User("Karina", 23);
            userRepository.saveAll(List.of(Marco, Karina));
        };
    }
}
