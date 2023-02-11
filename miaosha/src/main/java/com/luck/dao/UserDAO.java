package com.luck.dao;

import com.luck.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    User findById(Integer id);
}