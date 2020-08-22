package com.baizhi.travels.service;

import com.baizhi.travels.dao.PlaceDao;
import com.baizhi.travels.entity.Place;
import com.baizhi.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private ProvinceService provinceService;


    @Override
    public Place findOne(String id) {
        return placeDao.findOne(id);
    }

    @Override
    public void deleteAllByProvinceId(String id) {
        placeDao.deleteAllByProvinceId(id);
    }

    @Override
    public void updete(Place place) {
        placeDao.update(place);
    }

    @Override
    public void delete(String id) {
        //对省份操作景点数-1
        Place place = findOne(id);
        //获取到省份信息
        Province province = provinceService.finOne(place.getProvinceid());
        //更新省份信息
        province.setPlacecounts(province.getPlacecounts()-1);
        provinceService.update(province);
        //删除景点
        placeDao.delete(id);
    }

    @Override
    public void save(Place place) {
        //保存景点
        placeDao.save(place);
        //查询原始省份信息
        Province province = provinceService.finOne(place.getProvinceid());
        //更新省份信息的景点个数
        province.setPlacecounts(province.getPlacecounts()+1);
        provinceService.update(province);
    }

    @Override
    public List<Place> findAllByProvinceId(Integer page, Integer rows, String provinceId) {
        Integer start = (page-1)*rows;
        return placeDao.findAllByProvinceId(start,rows,provinceId);
    }

    @Override
    public Integer findTotalsByProvinceId(String provinceid) {
        return placeDao.findTotalsByProvinceId(provinceid);
    }
}
