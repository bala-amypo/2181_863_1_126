package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())          // disable CSRF (needed for PUT/DELETE/POST)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()          // allow all requests for all HTTP methods
            )
            .httpBasic().disable()                 // disable default HTTP Basic auth
            .formLogin().disable();                // disable login form

        return http.build();
    }
}