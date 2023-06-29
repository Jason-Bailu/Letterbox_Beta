package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.Menu;
import com.dxy.letterboxbackstage.mapper.MenuMapper;
import com.dxy.letterboxbackstage.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    MenuMapper menuMapper;

    public void alter() {
        menuMapper.alter();
    }

    public List<Menu> findAll() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        List<Menu> menus = list(wrapper);
        List<Menu> parentMenus = menus.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentMenus) {
            List<Menu> children = menus.stream().filter(cmenu -> menu.getId().equals(cmenu.getPid())).collect(Collectors.toList());
            menu.setChildren(children);
        }
        return parentMenus;
    }

    public List<Menu> find(String name, String description) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        if (StrUtil.isNotEmpty(name)) wrapper.like("menu_name", name);
        if (StrUtil.isNotEmpty(description)) wrapper.like("description", description);
        List<Menu> menus = list(wrapper);
        List<Menu> parentMenus = menus.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentMenus) {
            List<Menu> children = menus.stream().filter(cmenu -> menu.getId().equals(cmenu.getPid())).collect(Collectors.toList());
            menu.setChildren(children);
        }
        return parentMenus;
    }

    public List<Menu> findByMenuIds(List<Integer> menuIds) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id").in("id", menuIds);
        List<Menu> menus = list(wrapper);
        Set<Integer> menuPids = menus.stream().map(Menu::getPid).collect(Collectors.toSet());
        List<Menu> parentMenus = list(new QueryWrapper<Menu>().orderByAsc("id").in("id", menuPids));
        for (Menu menu : parentMenus) {
            List<Menu> children = menus.stream().filter(cmenu -> menu.getId().equals(cmenu.getPid())).collect(Collectors.toList());
            menu.setChildren(children);
        }
        return parentMenus;
    }
}
