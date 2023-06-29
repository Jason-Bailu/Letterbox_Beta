package com.dxy.letterboxbackstage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: JasonD
 * @date: 2023/6/24 20:59
 * @Description:
 */
@Component
public class SessionUtils {
    private static final String SESSION_PREFIX = "session:";
    private static final int SESSION_EXPIRY = 1800; //会话过期时间 单位秒 设置为半个小时
    private static final String HEARTBEAT_CHANNEL = "heartbeat";

    @Autowired
    RedisUtils redisUtils;

    // 创建会话
    public void createSession(String username) {
        String sessionKey = SESSION_PREFIX + username;
        redisUtils.set(sessionKey, username, SESSION_EXPIRY);
        System.out.println("createSession");
    }

    // 发送心跳 刷新会话key
    public void sendHeartbeat(String username) {
        String sessionKey = SESSION_PREFIX + username;
        redisUtils.getAndExpire(sessionKey, SESSION_EXPIRY);
        redisUtils.converAndSend(HEARTBEAT_CHANNEL, sessionKey);//发送心跳
        System.out.println("sendHeartbeat");
    }

    // schedule定时任务用于检查检查断开死连接会话
    public void checkAndDisconnectDeadSessions() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        for (String sessionKey : sessionKeys) {
            long remainingTime = redisUtils.getTTL(sessionKey);
            if (remainingTime == -2) {
                //会话已过期
                String username = sessionKey.substring(SESSION_PREFIX.length());
                System.out.println("Disconnecting dead session: " + username);
            }
        }
    }

    // 查看当前所有sessionkeys状态
    public void getAllKey() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        sessionKeys.forEach(key -> {
            System.out.println(key);
            System.out.println(redisUtils.getTTL(key));
        });
    }

    //查看制定key状态
    public Boolean getKey(String username) {
        long ttl = redisUtils.getTTL(SESSION_PREFIX + username);
        if (ttl == -2) {
            return false;
        }
        return true;
    }
}
