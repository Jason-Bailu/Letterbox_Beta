package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.Role;
import com.dxy.letterboxbackstage.entity.RoleMenu;
import com.dxy.letterboxbackstage.mapper.RoleMapper;
import com.dxy.letterboxbackstage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuServiceImpl roleMenuService;

    public void alter() {
        roleMapper.alter();
    }

    public Page<Role> findPage(Page<Role> page, String roleName, String description) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(roleName)) wrapper.like("role_name", roleName);
        if (StrUtil.isNotEmpty(description)) wrapper.like("description", description);
        return page(page, wrapper);
    }

    public List<Integer> getRoleMenu(Integer roleId) {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
        return roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Transactional
    public Boolean setRoleMenu(Integer roleId, List<Integer> menuIds) {
        roleMenuService.alter();
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        roleMenuService.remove(wrapper);
        for (Integer menuId : menuIds) {
            roleMenuService.save(new RoleMenu(roleId, menuId));
        }
        return true;
    }
}
