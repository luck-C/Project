package com.luck.dao;

import com.luck.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //登录
    User login(User user);

    User findByUserName(String name);

    void register(User user);

    User findRolesByUserName(String username);
}
