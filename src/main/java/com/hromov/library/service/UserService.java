package com.hromov.library.service;

import com.hromov.library.model.User;

public interface UserService {
    User getUserById(Long id);

    User register(User user);
}
