package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //登录
    User login(User user);

    User findByUserName(String name);

    void register(User user);

    User findRolesByUserName(String username);
}
