package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Update("alter table t_role_menu auto_increment=1")
    void alter();
}
