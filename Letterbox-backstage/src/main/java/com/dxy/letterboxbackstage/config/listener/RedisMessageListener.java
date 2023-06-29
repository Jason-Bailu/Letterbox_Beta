package com.dxy.letterboxbackstage.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: JasonD
 * @date: 2023/6/24 18:50
 * @Description:
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("channel: " + new String(pattern));
        String context = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
        System.out.println(context);
    }
}
