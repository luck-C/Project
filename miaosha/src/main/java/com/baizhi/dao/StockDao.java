package com.baizhi.dao;

import com.baizhi.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockDao {
    //检查库存
    Stock checkStock(Integer id);
    //更新库存
    int update(Stock stock);
}
