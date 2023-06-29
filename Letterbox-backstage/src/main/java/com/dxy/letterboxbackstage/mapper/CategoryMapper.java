package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Update("alter table t_category auto_increment=1")
    void alter();
}
