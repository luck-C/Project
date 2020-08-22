package com.baizhi.service;

import com.baizhi.entity.UserFiles;

import java.util.List;

public interface UserFilesService {
    //保存文件
     void save(UserFiles userFiles);
    //根据id查询所有文件
    List<UserFiles> findAllByUserId(Integer id);
    //根据id查询文件
    UserFiles finById(String id);
    //更新文件
    void update(UserFiles file);
    //删除文件
    void delete(String id);
}
