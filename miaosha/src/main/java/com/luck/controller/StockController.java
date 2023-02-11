package com.luck.controller;

import com.luck.dao.StockDao;
import com.luck.entity.Stock;
import com.luck.service.OrderService;
import com.luck.service.UserService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("stock")
@Slf4j
public class StockController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    @Autowired
    private StockDao stockDao;

    private RateLimiter rateLimiter = RateLimiter.create(20);

    /**
     * cas + 令牌桶 + 接口隐藏
     * */
    @GetMapping("/kill-token-md5-limit")
    public String killtokenlimit(Integer id,Integer userid,String md5) {
        //加入令牌桶的限流措施
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)) {
            log.info("抛弃请求: 抢购失败,当前秒杀活动过于火爆,请重试");
            return "抢购失败,当前秒杀活动过于火爆,请重试!";
        }
        try {
            //加入单用户限制调用频率
            int count = userService.saveUserCount(userid);
            log.info("用户截至该次的访问次数为: [{}]", count);
            boolean isBanned = userService.getUserCount(userid);
            if (isBanned) {
                log.info("购买失败,超过频率限制!");
                return "购买失败，超过频率限制!";
            }
            //根据秒杀商品id 去调用秒杀业务
            int orderId = orderService.kill(id,userid,md5);
            return "秒杀成功,订单id为: " + String.valueOf(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //生成md5值的方法
    @RequestMapping("/md5")
    public String getMd5(Integer id, Integer userid) {
        String md5;
        try {
            md5 = orderService.getMd5(id, userid);
        }catch (Exception e){
            e.printStackTrace();
            return "获取md5失败: "+e.getMessage();
        }
        return "获取md5信息为: "+md5;
    }

    /**
     * cas+令牌桶
     * */
    @GetMapping("/killToken/{id}")
    public String killToken(@PathVariable Integer id){
        if(!rateLimiter.tryAcquire(2,TimeUnit.SECONDS)){
            //log.info("活动过于火爆，抢购失败，请重试！");
            return "活动过于火爆，抢购失败！";
        }
        try {
            Stock stock = stockDao.selectById(id);
            //根据商品id创建订单,返回创建订单的id
            int orderId =  orderService.createOrder(stock);
            log.info("抢购成功！");

            return "秒杀成功，订单id为"+ String.valueOf(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @GetMapping("kill")
    public String kill(Integer id){
        try {
        int orderId = orderService.kill(id);
        return "秒杀成功，订单id为"+ String.valueOf(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }



    //秒杀方法
    @GetMapping("sale/{id}")
    public String sale(@PathVariable Integer id){
        int orderId = 0;
        try{
            Stock stock = stockDao.selectById(id);
            //根据商品id创建订单,返回创建订单的id
            orderId =  orderService.createOrder(stock);
            System.out.println("orderId = " + orderId);
            return String.valueOf(orderId);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
