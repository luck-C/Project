package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
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
        return userDao.login(user);
    }

    @Override
    public User findByUserName(String name) {
        return userDao.findByUserName(name);
    }

    @Override
    public void register(User user) {
        user.setRole(0);
        String salt = SaltUtils.getSalt(8);
        user.setSalt(salt);
        Md5Hash password = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(password.toHex());
        userDao.register(user);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }
}
