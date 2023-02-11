package com.luck.travels;

import com.luck.travels.entity.Province;
import com.luck.travels.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestProvinceService {
    @Autowired
    private ProvinceService provinceService;
    @Test
    public void testService(){
        List<Province> page = provinceService.findByPage(1, 4);
        for (Province p:page
             ) {
            System.out.println(p);
        }
    }
}
