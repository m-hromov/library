package com.hromov.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${enable-rest-security:false}")
    private boolean isRestSecurityEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        if (isRestSecurityEnabled) {
            httpSecurity.authorizeHttpRequests(auth ->
                    auth.anyRequest()
                            .authenticated());
        } else {
            httpSecurity.authorizeHttpRequests(auth ->
                            auth.anyRequest()
                                    .permitAll())
                    .csrf()
                    .disable()
                    .cors()
                    .disable();
        }
        return httpSecurity.build();
    }
}
