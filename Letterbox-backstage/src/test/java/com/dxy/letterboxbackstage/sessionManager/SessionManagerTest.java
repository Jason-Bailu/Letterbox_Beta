package com.dxy.letterboxbackstage.sessionManager;

import com.dxy.letterboxbackstage.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author: JasonD
 * @date: 2023/6/24 16:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SessionManagerTest.class)
@ComponentScan(basePackages = {"com.dxy.letterboxbackstage"})
public class SessionManagerTest {
    private static final String SESSION_PREFIX = "session:";
    private static final int SESSION_EXPIRY = 1800; //会话过期时间 单位秒
    private static final String HEARTBEAT_CHANNEL = "heartbeat";

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void sessionTest() {
        //模拟用户建立会话
        String sessionId = "session1";
        createSession(sessionId);

        //模拟心跳
        simulateHeartbeat(sessionId, 5);
        simulateHeartbeat(sessionId, 10);

        //模拟定期检查和断开连接
        checkAndDisconnectDeadSessions();
    }

    //创建会话
    private void createSession(String sessionId) {
        String sessionKey = SESSION_PREFIX + sessionId;
        redisUtils.set(sessionKey, "admin", SESSION_EXPIRY);
        System.out.println("createSession");
    }

    //模拟发送心跳
    private void simulateHeartbeat(String sessionId, int delaySeconds) {
        try {
            Thread.sleep(delaySeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sessionKey = SESSION_PREFIX + sessionId;
        redisUtils.getAndExpire(sessionKey, SESSION_EXPIRY);
        redisUtils.converAndSend(HEARTBEAT_CHANNEL, sessionKey);//发送心跳
        System.out.println("simulateHeartbeat");
    }

    //检查断开死连接会话
    private void checkAndDisconnectDeadSessions() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        for (String sessionKey : sessionKeys) {
            long remainingTime = redisUtils.getTTL(sessionKey);
            if (remainingTime == -2) {
                //会话已过期
                String sessionId = sessionKey.substring(SESSION_PREFIX.length());
                System.out.println("Disconnecting dead session: " + sessionId);
            }
        }
    }

    @Test
    public void getAllKey() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        sessionKeys.forEach(key -> {
            System.out.println(key);
            System.out.println(redisUtils.getTTL(key));
        });
    }
}
