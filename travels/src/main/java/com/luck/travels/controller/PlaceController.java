package com.luck.travels.controller;

import com.luck.travels.entity.Place;
import com.luck.travels.entity.Result;
import com.luck.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;
    /**
     * 更新景点信息
     * */
    @PostMapping("update")
    public Result update(MultipartFile pic,Place place) throws IOException {
        //封装返回的结果
        Result result = new Result();
        //采用base64来对图片进行编码
        String picPath = Base64Utils.encodeToString(pic.getBytes());
        place.setPicpath(picPath);
        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + dateFormat;
        File dateDir = new File(dateDirPath);
        if (!dateDir.exists()) dateDir.mkdirs();
        //上传文件处理
        String extension = "."+FilenameUtils.getExtension(pic.getOriginalFilename());
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extension;
        pic.transferTo(new File(dateDir,newFileName));
        try {
            placeService.updete(place);
            result.setMsg("景点信息更新成功！！！");
        }catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }
    /**
     * 查找一个景点信息
     * */
    @GetMapping("findOnePlace")
    public Place findOnePlace(String id){
        Place place = placeService.findOne(id);
        return place;
    }
    /**
     * 删除景点信息
     * */
    @GetMapping("delete")
    public Result delete(String placeId){
        Result result = new Result();
        try {
            placeService.delete(placeId);
            result.setMsg("删除景点信息成功！！！");
        }catch (Exception e){
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }
    /**
     * 保存景点信息
     * */
    @PostMapping("save")
    public Result save(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();
        try {
            //保存place对象
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            placeService.save(place);
            result.setMsg("保存景点信息成功！！！");
        }catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + dateFormat;
        File dateDir = new File(dateDirPath);
        if (!dateDir.exists()) dateDir.mkdirs();
        //上传文件处理
        String extension = "."+FilenameUtils.getExtension(pic.getOriginalFilename());
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extension;
        pic.transferTo(new File(dateDir,newFileName));
        return result;
    }
    /**
     * 查询所有  返回分页数据
     * */
    @GetMapping("findAll")
    public Map<String,Object> findAllPage(Integer page, Integer rows,String provinceId){
        page = page==null?1:page;
        rows = rows==null?4:page;
        Map<String,Object> map = new HashMap<>();
        List<Place> places = placeService.findAllByProvinceId(page, rows, provinceId);
        Integer counts= placeService.findTotalsByProvinceId(provinceId);
        Integer totalPage = counts%rows==0?counts/rows:counts/rows+1;
        map.put("places",places);
        map.put("totalPage",totalPage);
        map.put("counts",counts);
        map.put("page",page);
        return map;
    }
}
