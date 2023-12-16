package com.greenimpact.server.configuration;

import com.greenimpact.server.organization.OrganizationEntity;
import com.greenimpact.server.organization.OrganizationRepository;
import com.greenimpact.server.organization.OrganizationType;
import com.greenimpact.server.role.RoleEntity;
import com.greenimpact.server.role.RoleEnum;
import com.greenimpact.server.role.RoleRepository;
import com.greenimpact.server.user.UserEntity;
import com.greenimpact.server.user.UserRepository;
import com.greenimpact.server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository,
                                        OrganizationRepository organizationRepository) {
        return args -> {
            OrganizationEntity greenImpact = new OrganizationEntity(true, OrganizationType.MASTER, "GreenImpact");
            greenImpact = organizationRepository.save(greenImpact);

            OrganizationEntity greenPeace = new OrganizationEntity(true, OrganizationType.SUSTAINABLE_ENTITY, "GreenPeace");
            greenPeace = organizationRepository.save(greenPeace);

            UserEntity marco = new UserEntity("madalin@gmail.com", new BCryptPasswordEncoder().encode("123"), "Marco", "Madalin", "ca", "12345678");
            marco.setLoggedOrganization(greenImpact);
            marco = userRepository.save(marco);

            UserEntity karina = new UserEntity("karina@gmail.com", new BCryptPasswordEncoder().encode("123"), "Karina", "Decsei", "es", "12345678");
            karina.setLoggedOrganization(greenPeace);
            karina = userRepository.save(karina);

            RoleEntity roleMarco1 = new RoleEntity(marco, greenImpact, RoleEnum.SUPER);
            RoleEntity roleMarco2 = new RoleEntity(marco, greenPeace, RoleEnum.USER);

            RoleEntity roleKarina1 = new RoleEntity(karina, greenImpact, RoleEnum.EDITOR);
            RoleEntity roleKarina2 = new RoleEntity(karina, greenPeace, RoleEnum.ADMINISTRATOR);

            roleRepository.saveAll(List.of(roleMarco1, roleMarco2, roleKarina1, roleKarina2));
        };
    }
}
