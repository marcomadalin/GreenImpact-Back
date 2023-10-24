package com.greenimpact.server.service;

import com.greenimpact.server.model.UserDTO;
import com.greenimpact.server.model.UserDetails;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @LoadBalanced
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO response = restTemplate.getForObject("http://localhost:8000/user/users/getByUsername/{username}", UserDTO.class, username);
        return new com.greenimpact.server.model.UserDetails(response.getId(), response.getUsername(), response.getPassword(),
                response.getName(), response.getAge(), response.getLoggedOrganization(), response.getRole());
    }
}
