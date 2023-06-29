package com.dxy.letterboxbackstage.config.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.entity.Menu;
import com.dxy.letterboxbackstage.entity.User;
import com.dxy.letterboxbackstage.entity.UserRole;
import com.dxy.letterboxbackstage.exception.UserLockedException;
import com.dxy.letterboxbackstage.service.impl.*;
import com.dxy.letterboxbackstage.utils.JwtUtils;
import com.dxy.letterboxbackstage.utils.SessionUtils;
import com.dxy.letterboxbackstage.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author: JasonD
 * @date: 2023/5/19 23:32
 * @Description: 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private MenuServiceImpl menuService;

    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json,;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String username = authentication.getName();
        String token = JwtUtils.genJwtToken(username);

        User user = userService.getByUsername(username);
        Integer roleId = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", user.getId())).getRoleId();
        List<Integer> menuIds = roleService.getRoleMenu(roleId);
        List<Menu> menus = menuService.findByMenuIds(menuIds);
        List<MenuVO> menuVOS = new ArrayList<>();
        for (Menu menu : menus) {
            menuVOS.add(BeanUtil.copyProperties(menu, MenuVO.class));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("menus", menuVOS);

        //创建新会话
        sessionUtils.createSession(username);
        //开启定时任务
        Timer timer = new Timer(username);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sessionUtils.checkAndDisconnectDeadSessions();
                sessionUtils.getAllKey();
            }
        }, 90000, 90000);

        outputStream.write(JSONUtil.toJsonStr(Result.success("登录成功", map)).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
