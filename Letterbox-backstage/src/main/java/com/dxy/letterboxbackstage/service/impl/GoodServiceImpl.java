package com.dxy.letterboxbackstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.Good;
import com.dxy.letterboxbackstage.mapper.GoodMapper;
import com.dxy.letterboxbackstage.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信件配件表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {
    @Autowired
    GoodMapper goodMapper;

    public void alter() {
        goodMapper.alter();
    }
}
