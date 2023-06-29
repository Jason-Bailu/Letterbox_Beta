package com.dxy.letterboxbackstage.mapper;

import com.dxy.letterboxbackstage.entity.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 轮播图表 Mapper 接口
 * </p>
 *
 * @author dxy
 * @since 2023-06-17
 */
@Mapper
public interface BannerMapper extends BaseMapper<Banner> {
    @Update("alter table t_banner auto_increment=1")
    void alter();
}
