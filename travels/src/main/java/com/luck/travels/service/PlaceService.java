package com.luck.travels.service;

import com.luck.travels.entity.Place;

import java.util.List;

public interface PlaceService {
    //删除省份下的所有景点信息
    void deleteAllByProvinceId(String id);
    //更新景点
    void updete(Place place);
    //查找单个景点
    Place findOne(String id);

    //删除景点
    void delete(String id);

    List<Place> findAllByProvinceId(Integer page,Integer rows,String provinceid);

    Integer findTotalsByProvinceId(String provinceid);

    void save(Place place);
}
