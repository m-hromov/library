package com.hromov.library.config;

import com.hromov.library.filter.JwtAuthFilter;
import com.hromov.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationEntryPoint authEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;
    @Value("${enable-rest-security:true}")
    private boolean enableRestSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (false) {
            http.formLogin().disable()
                    .authorizeHttpRequests(auth ->
                            auth.requestMatchers("/books").permitAll()
                                    .requestMatchers("/auth").permitAll()
                                    .requestMatchers("/static/css/style.css").permitAll()
                                    .requestMatchers("/auth/login").permitAll()
                                    .requestMatchers("/swagger-ui**").permitAll()
                                    .requestMatchers("/swagger-ui/**").permitAll()
                                    .requestMatchers("/v3/**").permitAll()
                                    .requestMatchers("/books/{bookId}").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint)
                    .and()
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf()
                    .disable();
        } else {
            http.authorizeHttpRequests(auth ->
                            auth.anyRequest()
                                    .permitAll()
                    )
                    .cors()
                    .disable()
                    .csrf()
                    .disable();
        }
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder encoder) {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not found"));
    }
}
