package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.Letter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 信件表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-06-04
 */
@Mapper
public interface LetterMapper extends BaseMapper<Letter> {
    @Update("alter table t_letter auto_increment=1")
    void alter();
}
