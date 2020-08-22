package com.baizhi.dao;

import com.baizhi.entity.UserFiles;

import java.util.List;

public interface UserFilesDao {
    //根据userId查文件
    List<UserFiles> findAllByuid(Integer id);
    //保存文件
    void save(UserFiles userFiles);
    //根据id查询文件
    UserFiles findById(String id);
    //更新下载次数
    void update(UserFiles file);
    //根据id删除记录
    void delete(String id);
}
