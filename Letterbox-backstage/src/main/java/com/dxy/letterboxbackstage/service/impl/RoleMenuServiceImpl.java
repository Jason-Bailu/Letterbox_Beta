package com.dxy.letterboxbackstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.RoleMenu;
import com.dxy.letterboxbackstage.mapper.RoleMenuMapper;
import com.dxy.letterboxbackstage.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    public void alter() {
        roleMenuMapper.alter();
    }
}
