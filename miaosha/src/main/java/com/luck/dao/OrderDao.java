package com.luck.dao;

import com.luck.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {
    //创建订单
    void save(Order order);
}
