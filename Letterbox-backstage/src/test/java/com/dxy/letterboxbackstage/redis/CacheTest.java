package com.dxy.letterboxbackstage.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: JasonD
 * @date: 2023/6/24 16:59
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheTest.class)
@ComponentScan(basePackages = {"com.dxy.letterboxbackstage"})
public class CacheTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "dxy");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
