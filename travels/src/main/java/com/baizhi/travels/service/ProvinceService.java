package com.baizhi.travels.service;

import com.baizhi.travels.entity.Province;

import java.util.List;

public interface ProvinceService {
    //更新
    void update(Province province);
    //查询一个
    Province finOne(String id);
    //省份删除
    void delete(String id);
    //省份添加
    void save(Province province);
    //参数1：当前页 参数2：每页显示记录数
    List<Province> findByPage(Integer page,Integer rows);

    //查询总条数
    Integer findTotals();
}
