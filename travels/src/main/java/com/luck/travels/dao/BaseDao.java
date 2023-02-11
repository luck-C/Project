package com.luck.travels.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T,K> {
    T findOne(K k);
    void save(T t);
    void update(T t);
    void delete(K k);
    List<T> findAll();
    List<T> findByPage(@Param("start") Integer start,@Param("rows") Integer rows);
    Integer findTotals();
}
