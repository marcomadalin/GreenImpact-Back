package com.example.gateway.security;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class AuthenticationFilter implements GlobalFilter {

    private final RestTemplate template;

    private final RouteValidator validator;

    private final DiscoveryClient discoveryClient;

    public AuthenticationFilter(RestTemplate template, RouteValidator validator, DiscoveryClient discoveryClient) {
        this.template = template;
        this.validator = validator;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (validator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("MISSING AUTHORIZATION HEADER");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) token = authHeader.substring(7);
            else throw new RuntimeException("MISSING BEARER TOKEN");

            String usersServiceUrl = getServiceUrl("user");
            String url = usersServiceUrl + "/authentication/validate?token=" + token;

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);

            ResponseEntity<Boolean> response = template.exchange(url, HttpMethod.GET, httpEntity, Boolean.class);
            if (Boolean.FALSE.equals(response.getBody()))
                throw new RuntimeException("UNAUTHORIZED ACCESS TO APPLICATION");

        }
        return chain.filter(exchange);
    }

    private String getServiceUrl(String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .findFirst()
                .map(serviceInstance -> serviceInstance.getUri().toString())
                .orElseThrow(() -> new RuntimeException("Service not found in Eureka: " + serviceName));
    }

}