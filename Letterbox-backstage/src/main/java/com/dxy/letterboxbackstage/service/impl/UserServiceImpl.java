package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.dto.UserDTO;
import com.dxy.letterboxbackstage.entity.Menu;
import com.dxy.letterboxbackstage.entity.Role;
import com.dxy.letterboxbackstage.entity.User;
import com.dxy.letterboxbackstage.entity.UserRole;
import com.dxy.letterboxbackstage.exception.ServiceExecption;
import com.dxy.letterboxbackstage.exception.UserLockedException;
import com.dxy.letterboxbackstage.mapper.MenuMapper;
import com.dxy.letterboxbackstage.mapper.RoleMapper;
import com.dxy.letterboxbackstage.mapper.UserMapper;
import com.dxy.letterboxbackstage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    @Lazy
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    public void alter() {
        userMapper.alter();
    }

    public Boolean login(UserDTO userDto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDto.getUsername()).eq("password", userDto.getPassword());
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return false;
        }
        //配置token进行返回
        BeanUtil.copyProperties(user, userDto);
        return true;
    }

    public Boolean register(UserDTO userDto) {
        alter();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDto.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            throw new ServiceExecption(CodeEnum.C400, "用户名重复");
        } else {
            user = new User();
            String encodePassword = encoder.encode(userDto.getPassword());
            BeanUtil.copyProperties(userDto, user, "password");
            user.setPassword(encodePassword);
            user.setRoleName("ROLE_COMMON");
            if (save(user)) {
                Integer id = (Integer) getMap(new QueryWrapper<User>().eq("username", user.getUsername())).get("id");
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(4);
                return userRoleService.save(userRole);
            } else {
                return false;
            }
        }
    }

    public User getByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("enable", 1);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new UserLockedException("用户已被禁止，请联系管理员");
        }
        return user;
    }

    public String getUserAuthorityInfo(Integer userId) {
        StringBuilder authority = new StringBuilder();
        //根据用户id获取用户的所有角色信息
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().inSql("id", "select role_id from t_user_role where user_id=" + userId));
        if (roles.size() > 0) {
            String roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.joining(","));
            authority.append(roleNames);
        }
        //根据角色信息获取对应的权限信息，而且不要重复
        Set<String> sets = new HashSet<>();
        for (Role role : roles) {
            List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().inSql("id", "select menu_id from t_role_menu where role_id=" + role.getId()));
            for (Menu menu : menus) {
                String perms = menu.getPerms();
                if (StrUtil.isNotEmpty(perms)) {
                    sets.add(perms);
                }
            }
        }
        if (sets.size() > 0) {
            authority.append(",");
            String collect = String.join(",", sets);
            authority.append(collect);
        }
        System.out.println("authority: " + authority.toString());
        return authority.toString();
    }

    public Boolean setUserRole(Integer userId, String roleName) {
        userRoleService.alter();
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_name", roleName));
        Integer roleId = role.getId();
        UserRole one = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", userId).eq("role_id", roleId));
        if (one != null) {
            return true;
        } else {
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRoleService.save(userRole);
        }
    }
}
