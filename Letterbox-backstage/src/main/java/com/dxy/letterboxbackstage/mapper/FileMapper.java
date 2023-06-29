package com.dxy.letterboxbackstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxy.letterboxbackstage.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-05-15
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {
    @Update("alter table t_file auto_increment=1")
    void alter();
}
