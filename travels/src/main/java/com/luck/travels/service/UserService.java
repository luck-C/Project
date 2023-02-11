package com.luck.travels.service;

import com.luck.travels.entity.User;

public interface UserService {
    User login(User user);
    void register(User user);
}
