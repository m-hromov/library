package com.hromov.library.service.impl;

import com.hromov.library.exception.NotFoundException;
import com.hromov.library.model.Authority;
import com.hromov.library.model.User;
import com.hromov.library.model.auth.SecurityToken;
import com.hromov.library.repository.UserRepository;
import com.hromov.library.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;
    @Value("${security.secret}")
    private String secret;

    public SecurityToken getSecurityToken(User user) {
        String access = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(Instant.now()
                                .plus(60, ChronoUnit.MINUTES))
                )
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return SecurityToken.builder()
                .accessToken(access)
                .build();
    }

    public User getUser(String token) {
        Long userId = Integer.toUnsignedLong((Integer) Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("userId"));
        return userRepository.findById(userId)
                .orElseThrow(() -> {throw new NotFoundException("User was not found.");});
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("Error validating jwt", e);
            return false;
        }
    }
}
