package com.baizhi.travels;

import com.baizhi.travels.entity.User;
import com.baizhi.travels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {
    @Autowired
    private UserService userService;
    @Test
    public void testService(){
        User user = new User();
        user.setUsername("xiaochen");
        user.setPassword("1234");
        user.setEmail("23232@qq.com");
        userService.register(user);
    }
}
