package com.baizhi.travels.service;

import com.baizhi.travels.dao.ProvinceDao;
import com.baizhi.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService{
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private PlaceService placeService;
    @Override
    public Integer findTotals() {
        return provinceDao.findTotals();
    }

    @Override
    public void update(Province province) {
        provinceDao.update(province);
    }

    @Override
    public Province finOne(String id) {
        return provinceDao.findOne(id);
    }

    @Override
    public void save(Province province) {
        province.setPlacecounts(0);

        provinceDao.save(province);
    }

    @Override
    public List<Province> findByPage(Integer page, Integer rows) {
        int start = (page-1)*rows;
        return provinceDao.findByPage(start,rows);

    }

    @Override
    public void delete(String id) {
        placeService.deleteAllByProvinceId(id);
        provinceDao.delete(id);
    }
}
