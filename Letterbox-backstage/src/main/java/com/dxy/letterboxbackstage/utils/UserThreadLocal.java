package com.dxy.letterboxbackstage.utils;

import com.dxy.letterboxbackstage.vo.UserVO;

/**
 * @Author: JasonD
 * @date: 2023/5/21 21:08
 * @Description: ThreadLocal类用于保存当前登录的用户信息
 */
public class UserThreadLocal {
    private UserThreadLocal() {}

    private static final ThreadLocal<UserVO> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserVO userVo) {
        THREAD_LOCAL.set(userVo);
    }

    public static UserVO get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
