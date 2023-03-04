package com.hromov.library.controller;

import com.hromov.library.dto.UserDto;
import com.hromov.library.model.User;
import com.hromov.library.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.hromov.library.controller.AuthenticationController.AUTHENTICATION_BASE;

@RestController
@RequestMapping(AUTHENTICATION_BASE)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String AUTHENTICATION_BASE = "auth";
    public static final String REGISTER = "register";
    public static final String LOGOUT = "logout";

    private final UserService userService;

    @GetMapping
    public ModelAndView loadPage() {
        return new ModelAndView("signUp");
    }

    @Hidden
    @PostMapping(REGISTER)
    public void register(UserDto userLoginRequest) {
        User user = User.builder()
                .username(userLoginRequest.getUsername())
                .password(userLoginRequest.getPassword())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .accountNonLocked(true)
                .build();
        userService.register(user);
    }

    @PostMapping(LOGOUT)
    public void fakeLogout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
