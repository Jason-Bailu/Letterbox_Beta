package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Update("alter table t_user_role auto_increment=1")
    void alter();
}
