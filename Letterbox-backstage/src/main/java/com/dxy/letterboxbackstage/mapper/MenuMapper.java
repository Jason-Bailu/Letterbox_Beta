package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Update("alter table t_menu auto_increment=1")
    void alter();
}
