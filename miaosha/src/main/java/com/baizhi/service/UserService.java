package com.baizhi.service;

public interface UserService {
    //保存用户访问次数
    int saveUserCount(Integer userid);
    //获得用户请求次数
    boolean getUserCount(Integer userid);
}
