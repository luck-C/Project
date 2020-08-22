package com.baizhi.travels.cache;

import com.baizhi.travels.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisCache implements Cache {
    private  String id;

    public RedisCache(String id) {
        //System.out.println("id:" + id);
        this.id = id;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        System.out.println("key:"+key+",value:"+value);
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForHash().put(id.toString(),key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate.opsForHash().get(id.toString(),key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        System.out.println("删除缓存");
        return null;
    }
    //数据变动时会使用该方法
    @Override
    public void clear() {
        System.out.println("清除缓存");
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }
}
