package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Update("alter table t_role auto_increment=1")
    void alter();
}
