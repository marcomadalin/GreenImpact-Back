package com.greenimpact.server;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.organization.OrganizationRepository;
import com.greenimpact.server.role.RoleEntity;
import com.greenimpact.server.role.RoleEnum;
import com.greenimpact.server.role.RoleRepository;
import com.greenimpact.server.user.UserEntity;
import com.greenimpact.server.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, OrganizationRepository organizationRepository) {
        return args -> {
            UserEntity marco = new UserEntity("marcomadalin", "123", "Marco", 23);
            marco = userRepository.save(marco);

            UserEntity karina = new UserEntity("karinad", "123", "Karina", 23);
            karina = userRepository.save(karina);

            OrganizationEntity greenImpact = new OrganizationEntity("GreenImpact");
            greenImpact = organizationRepository.save(greenImpact);

            OrganizationEntity greenPeace = new OrganizationEntity("GreenPeace");
            greenPeace = organizationRepository.save(greenPeace);

            RoleEntity roleMarco1 = new RoleEntity(marco, greenImpact, RoleEnum.ADMINISTRATOR);
            RoleEntity roleMarco2 = new RoleEntity(marco, greenPeace, RoleEnum.USER);

            RoleEntity roleKarina1 = new RoleEntity(karina, greenImpact, RoleEnum.EDITOR);
            RoleEntity roleKarina2 = new RoleEntity(karina, greenPeace, RoleEnum.ADMINISTRATOR);

            roleRepository.saveAll(List.of(roleMarco1, roleMarco2, roleKarina1, roleKarina2));
        };
    }
}
