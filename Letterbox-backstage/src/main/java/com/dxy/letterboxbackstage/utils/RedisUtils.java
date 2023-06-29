package com.dxy.letterboxbackstage.utils;

import cn.hutool.core.util.StrUtil;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.exception.ServiceExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: JasonD
 * @date: 2023/6/24 18:57
 * @Description:
 */
@Component
public class RedisUtils {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 发布消息
     */
    public boolean converAndSend(String channel, String message) {
        if (!StrUtil.hasLetter(channel)) {
            return false;
        }
        try {
            redisTemplate.convertAndSend(channel, message);
            System.out.println("send: " + message);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis订阅异常");
        }
    }

    /**
     * 添加key
     */
    public boolean set(String key, Object data, Integer expireTime) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, data, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis添加key异常");
        }
    }

    /**
     * 更新key
     */
    public boolean getAndExpire(String key, Integer expireTime) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().getAndExpire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis更新key异常");
        }
    }

    /**
     * 获取多个key
     */
    public Set<String> getMultiKeys(String keyPrefix) {
        Set<String> set = redisTemplate.keys(keyPrefix+"*");
        return set;
    }

    /**
     * 获取key的时间 -1就是没有过期时间，-2没有该key
     */
    public long getTTL(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     */
    public boolean getAndDelete(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis删除key异常");
        }
    }
}
