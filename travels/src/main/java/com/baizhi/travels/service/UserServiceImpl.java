package com.baizhi.travels.service;

import com.baizhi.travels.dao.UserDao;
import com.baizhi.travels.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        User userDB = userDao.findByUserName(user.getUsername());
        if(userDB != null){
        //判断密码
            if(userDB.getPassword().equals(user.getPassword())){
                return userDB;
            }
            throw new RuntimeException("密码输入错误!!!");
        }else {
            throw new RuntimeException("用户名输入错误!!!");
        }

    }

    @Override
    public void register(User user) {
        if(userDao.findByUserName(user.getUsername())==null){
            userDao.save(user);
        }else {
            throw new RuntimeException("用户名已存在！！");
        }

    }
}
