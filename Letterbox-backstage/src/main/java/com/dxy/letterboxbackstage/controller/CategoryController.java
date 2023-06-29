package com.dxy.letterboxbackstage.controller;


import cn.hutool.core.bean.BeanUtil;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.entity.Category;
import com.dxy.letterboxbackstage.service.impl.CategoryServiceImpl;
import com.dxy.letterboxbackstage.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Api(tags = "分类接口")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/all")
    @Secured({"ROLE_SUPER", "ROLE_GOOD", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:good:adm')")
    public Result getAll() {
        List<Category> list = categoryService.list();
        List<CategoryVO> categoryVOS = new ArrayList<>(16);
        for (Category category : list) {
            CategoryVO categoryVO = BeanUtil.copyProperties(category, CategoryVO.class);
            categoryVOS.add(categoryVO);
        }
        return Result.success(categoryVOS);
    }

}

