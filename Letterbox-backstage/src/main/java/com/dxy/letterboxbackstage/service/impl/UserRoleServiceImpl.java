package com.dxy.letterboxbackstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.UserRole;
import com.dxy.letterboxbackstage.mapper.UserRoleMapper;
import com.dxy.letterboxbackstage.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public void alter() {
        userRoleMapper.alter();
    }
}
