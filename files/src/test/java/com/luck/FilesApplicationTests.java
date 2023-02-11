package com.luck;

import com.luck.entity.Role;
import com.luck.entity.User;
import com.luck.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = FilesApplication.class)
@RunWith(SpringRunner.class)
public class FilesApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Test
    public void testShiroRole(){
        User tom = userService.findRolesByUserName("tom");
        List<Role> roles = tom.getRoles();
        roles.forEach(role -> {
            System.out.println(role.getName());
        });
    }
    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("age","19");
        System.out.println(redisTemplate.opsForValue().get("age"));
    }

}
