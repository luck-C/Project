package com.luck.service;

import com.luck.dao.OrderDao;
import com.luck.dao.StockDao;
import com.luck.dao.UserDAO;
import com.luck.entity.Order;
import com.luck.entity.Stock;
import com.luck.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StockDao stockDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDAO userDAO;


    @Override
    public int kill(Integer id, Integer userid, String md5) {
        //校验redis中秒杀商品是否超时
        //        if(!stringRedisTemplate.hasKey("kill"+id))
        //            throw new RuntimeException("当前商品的抢购活动已经结束啦~~");
        //先验证签名
        String hashKey = "KEY_"+userid+"_"+id;
        String s = stringRedisTemplate.opsForValue().get(hashKey);
        if (s==null) throw  new RuntimeException("没有携带验证签名,请求不合法!");
        if (!s.equals(md5)) throw  new RuntimeException("当前请求数据不合法,请稍后再试!");
        //校验库存
        Stock stock = checkStock(id);
        //更新库存
        updateSale(stock);
        //创建订单
        return createOrder(stock);
    }

    @Override
    public String getMd5(Integer id, Integer userid) {
        //检验用户的合法性
        User user = userDAO.findById(userid);
        if(user==null)throw new RuntimeException("用户信息不存在!");
        log.info("用户信息:[{}]",user.toString());
        //检验商品的合法行
        Stock stock = stockDao.checkStock(id);
        if(stock==null) throw new RuntimeException("商品信息不合法!");
        log.info("商品信息:[{}]",stock.toString());
        //生成hashkey
        String hashKey = "KEY_"+userid+"_"+id;
        //生成md5//这里!QS#是一个盐 随机生成
        String key = DigestUtils.md5DigestAsHex((userid+id+"!Q*jS#").getBytes());
        stringRedisTemplate.opsForValue().set(hashKey, key, 3600, TimeUnit.SECONDS);
        log.info("Redis写入：[{}] [{}]", hashKey, key);
        return key;
    }

    @Override
    public Integer createOrder(Stock stock) {
        //先检查库存
        checkStock(stock.getId());
        //更新库存
        updateSale(stock);
        //生成订单
        Order order = new Order();
        order.setName(stock.getName()).setSid(stock.getId()).setCreationDate(new Date());
        orderDao.save(order);
        return order.getId();
    }

    @Override
    public int kill(Integer id) {
        //先校验redis中是否过期
        if(!stringRedisTemplate.hasKey("kill"+id)){
            throw new RuntimeException("当前的商品抢购活动已结束！");
        }
            //查询库存
        Stock stock = checkStock(id);
            //更新库存
            updateSale(stock);
            //创建订单
            return createOrder(stock);
    }

    //查询库存
    private Stock checkStock(Integer id){
        Stock stock = stockDao.checkStock(id);
        if(stock.getCount().equals(stock.getSale())){
            throw new RuntimeException("库存不足，抢购失败！");
        }
        return stock;
    }
    //扣除库存
    private void updateSale(Stock stock){
        //扣除库存时在数据库层面实现
        int updaterows=stockDao.update(stock);
        if(updaterows == 0){
            throw new RuntimeException("抢购失败，请重试！");
        }
    }
    //创建订单
    /*private Integer createOrder(Stock stock){
        Order order = new Order();
        order.setName(stock.getName()).setSid(stock.getId()).setCreate_time(new Date());
        orderDao.save(order);
        return order.getId();
    }*/
}
