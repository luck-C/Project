package com.luck.travels.dao;

import com.luck.travels.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findByUserName(String username);
    void save(User user);
}
