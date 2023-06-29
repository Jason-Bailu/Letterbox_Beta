package com.dxy.letterboxbackstage.redis;

import com.dxy.letterboxbackstage.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: JasonD
 * @date: 2023/6/24 19:06
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMessageTest.class)
@ComponentScan(basePackages = {"com.dxy.letterboxbackstage"})
public class RedisMessageTest {
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test() {
        //发布消息
        String context = "hello session";
        redisUtils.converAndSend("SESSION", context);
    }
}
