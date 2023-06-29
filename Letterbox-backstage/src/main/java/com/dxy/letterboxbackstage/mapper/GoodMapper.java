package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 信件配件表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    @Update("alter table t_good auto_increment=1")
    void alter();
}
