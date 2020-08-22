package com.baizhi.service;

public interface OrderService {
    //秒杀方法
    int kill(Integer id);
    //id,userid,md5
    int kill(Integer id,Integer userid,String md5);
    //获得md5
    String getMd5(Integer id, Integer userid);
}
