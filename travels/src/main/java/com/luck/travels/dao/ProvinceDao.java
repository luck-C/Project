package com.luck.travels.dao;

import com.luck.travels.entity.Province;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProvinceDao extends BaseDao<Province,String> {
}
