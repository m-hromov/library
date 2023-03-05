package com.hromov.library.service;

import com.hromov.library.model.User;
import com.hromov.library.model.auth.SecurityToken;

public interface UserService {
    User getUserById(Long id);

    SecurityToken login(User user);
}
