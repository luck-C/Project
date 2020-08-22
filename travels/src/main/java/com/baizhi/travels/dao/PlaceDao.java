package com.baizhi.travels.dao;

import com.baizhi.travels.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PlaceDao extends BaseDao<Place,String > {
    List<Place> findAllByProvinceId(@Param("start") Integer start,@Param("rows") Integer rows,@Param("provinceId") String provinceId);

    Integer findTotalsByProvinceId(String provinceId);

    void deleteAllByProvinceId(String id);
}
