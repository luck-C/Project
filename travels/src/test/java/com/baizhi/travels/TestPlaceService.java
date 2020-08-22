package com.baizhi.travels;

import com.baizhi.travels.entity.Place;
import com.baizhi.travels.service.PlaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestPlaceService {
    @Autowired
    private PlaceService placeService;
    @Test
    public void testService(){
        List<Place> places = placeService.findAllByProvinceId(1, 3, "1");
        for (Place p:places
             ) {
            System.out.println(p);
        }
    }
}
