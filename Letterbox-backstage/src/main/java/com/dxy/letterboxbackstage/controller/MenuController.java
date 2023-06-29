package com.dxy.letterboxbackstage.controller;


import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.entity.Menu;
import com.dxy.letterboxbackstage.service.impl.MenuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Api(tags = "菜单的接口")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuServiceImpl menuService;

    @ApiOperation("获取所有菜单信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:menu:adm')")
    @GetMapping("/all")
    public Result findAll() {
        List<Menu> menus = menuService.findAll();
        return Result.success(menus);
    }

    @ApiOperation("菜单的模糊查询")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:menu:adm')")
    @GetMapping("/find")
    public Result find(@RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String description) {
        List<Menu> menus = menuService.find(name, description);
        return Result.success(menus);
    }

    @ApiOperation("菜单的保存/更新")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:menu:adm')")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Menu menu) {
        menuService.alter();
        boolean result = menuService.saveOrUpdate(menu);
        if (!result) return Result.error();
        return Result.success();
    }

    @ApiOperation("批量删除菜单信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:menu:adm')")
    @PostMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        boolean result = menuService.removeBatchByIds(ids);
        if (!result) return Result.error();
        return Result.success();
    }

    @ApiOperation("根据id删除菜单")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:menu:adm')")
    @DeleteMapping("/delete")
    public Result deleteById(@RequestParam Integer id) {
        boolean result = menuService.removeById(id);
        if (!result) return Result.error();
        return Result.success();
    }
}

