package com.dxy.letterboxbackstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.Category;
import com.dxy.letterboxbackstage.mapper.CategoryMapper;
import com.dxy.letterboxbackstage.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public void alter() {
        categoryMapper.alter();
    }

}
