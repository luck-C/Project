package com.luck.service;

import com.luck.entity.User;

public interface UserService {
    //登录方法
    User login(User user);

    User findByUserName(String name);

    void register(User user);

    User findRolesByUserName(String username);
}
