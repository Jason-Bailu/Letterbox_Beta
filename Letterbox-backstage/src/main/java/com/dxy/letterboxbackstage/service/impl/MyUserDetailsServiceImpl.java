package com.dxy.letterboxbackstage.service.impl;

import com.dxy.letterboxbackstage.entity.User;
import com.dxy.letterboxbackstage.exception.UserLockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: JasonD
 * @date: 2023/5/20 09:39
 * @Description: 自定义UserDetails
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        } else if (!user.getEnable()) {
            throw new UserLockedException("用户已被禁用，请联系管理员");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getUserAuthority(user.getId()));
    }

    //这里实现权限查询和设置
    public List<GrantedAuthority> getUserAuthority(Integer userId) {
        //这里传入一个字符串由逗号隔开
        //格式 角色格式：ROLE_ADMIN,ROLE_COMMON，权限格式：sys:user:resetPwd,sys:role:delete,sys:user:list
        //获取登录用户的权限信息
        String authority = userService.getUserAuthorityInfo(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
