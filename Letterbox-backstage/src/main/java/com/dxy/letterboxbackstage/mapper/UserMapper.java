package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("alter table t_user auto_increment=1")
    void alter();
}
