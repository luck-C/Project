package com.baizhi.service;

import com.baizhi.dao.UserFilesDao;
import com.baizhi.entity.UserFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class UserFilesServiceImpl implements UserFilesService{
    @Autowired
    private UserFilesDao userFilesDao;

    @Override
    public void save(UserFiles userFiles) {
        //userFiles.setIsImg();

        boolean image = userFiles.getType().startsWith("image");
        String isImag = image == true?"是":"否";
        userFiles.setIsImg(isImag);
        userFiles.setDownCount(0);
        userFiles.setUploadTime(new Date());
        userFilesDao.save(userFiles);
    }

    @Override
    public List<UserFiles> findAllByUserId(Integer id) {

        return userFilesDao.findAllByuid(id);
    }

    @Override
    public UserFiles finById(String id) {
        return userFilesDao.findById(id);
    }

    @Override
    public void update(UserFiles file) {
        userFilesDao.update(file);
    }

    @Override
    public void delete(String id) {
        userFilesDao.delete(id);
    }
}
