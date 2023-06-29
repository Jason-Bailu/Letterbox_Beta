package com.dxy.letterboxbackstage.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.dto.GoodDTO;
import com.dxy.letterboxbackstage.entity.Category;
import com.dxy.letterboxbackstage.entity.Good;
import com.dxy.letterboxbackstage.service.impl.CategoryServiceImpl;
import com.dxy.letterboxbackstage.service.impl.GoodServiceImpl;
import com.dxy.letterboxbackstage.vo.GoodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 信件配件表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-06-22
 */
@Api(tags = "配置接口")
@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    GoodServiceImpl goodService;

    @Autowired
    CategoryServiceImpl categoryService;

    @ApiOperation("配置分页显示")
    @GetMapping("/page")
    @Secured({"ROLE_SUPER", "ROLE_GOOD", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:good:adm')")
    public Result getAll(@RequestParam String categoryName, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<Good> page = new Page<>(pageNum, pageSize);
        Page<Good> result = null;
        if (categoryName.isBlank() || categoryName.isEmpty()) {
            result = goodService.page(page);
        } else {
            result = goodService.page(page, new QueryWrapper<Good>().eq("class_name", categoryName));
        }
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<Good> list = result.getRecords();
        List<GoodVO> goodVOs = new ArrayList<>(16);
        for (Good good : list) {
            GoodVO goodVO = BeanUtil.copyProperties(good, GoodVO.class);
            goodVOs.add(goodVO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", goodVOs);
        return Result.success(map);
    }

    @ApiOperation("根据类别查找配置")
    @GetMapping("/find/{className}")
    @Secured({"ROLE_SUPER", "ROLE_GOOD", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:good:adm')")
    public Result findByClassName(@PathVariable String className, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<Good> page = new Page<>(pageNum, pageSize);
        Page<Good> result = goodService.page(page, new QueryWrapper<Good>().eq("class_name", className));
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<Good> list = result.getRecords();
        List<GoodVO> goods = new ArrayList<>(16);
        for (Good good : list) {
            GoodVO goodVO = BeanUtil.copyProperties(good, GoodVO.class);
            goods.add(goodVO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", goods);
        return Result.success(map);
    }

    @ApiOperation("更新单个配置")
    @PostMapping("/update")
    @Secured({"ROLE_SUPER", "ROLE_GOOD", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:good:adm')")
    public Result updateRepository(@RequestBody GoodDTO goodDTO) {
        Good good = BeanUtil.copyProperties(goodDTO, Good.class);
        Boolean result = null;
        if (good.getId() == null) {
            goodService.alter();
            result = goodService.saveOrUpdate(good);
            Category category = categoryService.getOne(new QueryWrapper<Category>().eq("class_name", good.getClassName()));
            category.setCount(category.getCount() + 1);
            categoryService.update(category, new QueryWrapper<Category>().eq("id", category.getId()));
        } else {
            goodService.alter();
            QueryWrapper<Good> idWrapper = new QueryWrapper<Good>().eq("id", good.getId());
            result = goodService.saveOrUpdate(good, idWrapper);
        }
        if (result) return Result.success("成功！", null);
        return Result.error(CodeEnum.C500, "失败！");
    }

    @ApiOperation("删除单个配置")
    @PostMapping("/delete")
    @Secured({"ROLE_SUPER", "ROLE_GOOD", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:good:adm')")
    public Result deleteById(@RequestBody GoodDTO goodDTO) {
        Category category = categoryService.getOne(new QueryWrapper<Category>().eq("class_name", goodDTO.getClassName()));
        if (category.getCount() <= 1) return Result.error(CodeEnum.C600, "该类别数量");
        category.setCount(category.getCount() - 1);
        categoryService.update(category, new QueryWrapper<Category>().eq("id", category.getId()));
        goodService.alter();
        boolean result = goodService.removeById(goodDTO.getId());
        if (result) return Result.success("删除成功！");
        return Result.error(CodeEnum.C600, "删除失败！");
    }

    @ApiOperation("获取所有配置")
    @GetMapping("/all")
    public Result getAllByClassName(@RequestParam String paper, @RequestParam String cover, @RequestParam(defaultValue = "zh") String zhink, @RequestParam(defaultValue = "en") String enink) {
        List<Good> paperGood = goodService.list(new QueryWrapper<Good>().eq("class_name", paper));
        List<Good> coverGood = goodService.list(new QueryWrapper<Good>().eq("class_name", cover));
        List<Good> zhInkGood = goodService.list(new QueryWrapper<Good>().likeRight("good_name", zhink));
        List<Good> enInkGood = goodService.list(new QueryWrapper<Good>().likeRight("good_name", enink));
        if (paperGood.size() == 0 || coverGood.size() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        Map<String, List> map = new HashMap<>(16);
        map.put("paper", paperGood);
        map.put("cover", coverGood);
        map.put("zhink", zhInkGood);
        map.put("enink", enInkGood);
        return Result.success(map);
    }
}

