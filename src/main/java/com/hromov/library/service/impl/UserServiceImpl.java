package com.hromov.library.service.impl;

import com.hromov.library.exception.BusinessException;
import com.hromov.library.exception.NotFoundException;
import com.hromov.library.model.Authority;
import com.hromov.library.model.User;
import com.hromov.library.model.auth.SecurityToken;
import com.hromov.library.repository.UserRepository;
import com.hromov.library.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("User was not found.");
                });
    }

    @Override
    public SecurityToken login(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() &&
            !passwordEncoder.matches(user.getPassword(),
                    existingUser.get().getPassword())) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(Authority.builder().authority("USER").build()));
        return jwtService.getSecurityToken(
                existingUser.or(() -> Optional.of(userRepository.save(user)))
                        .get()
        );
    }


}
