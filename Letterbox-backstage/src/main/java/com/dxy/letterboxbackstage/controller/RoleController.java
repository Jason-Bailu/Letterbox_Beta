package com.dxy.letterboxbackstage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.entity.Role;
import com.dxy.letterboxbackstage.service.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-05-21
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @ApiOperation("角色分页信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> result = roleService.page(page);
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<Role> records = result.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", records);
        return Result.success(map);
    }

    @ApiOperation("模糊查询角色信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @GetMapping("/find")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String roleName, @RequestParam(defaultValue = "") String description) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> result = roleService.findPage(page, roleName, description);
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<Role> records = result.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", records);
        return Result.success(map);
    }

    @ApiOperation("添加/更新角色信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @PostMapping("/save")
    public Result saveOrUpdate(@RequestBody Role role) {
        roleService.alter();
        boolean result = roleService.saveOrUpdate(role);
        if (!result) return Result.error();
        return Result.success();
    }

    @ApiOperation("批量删除角色")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @PostMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        roleService.alter();
        boolean result = roleService.removeBatchByIds(ids);
        if (!result) return Result.error();
        return Result.success();
    }

    @ApiOperation("根据id删除角色")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @DeleteMapping("/delete")
    public Result deleteById(@RequestParam Integer id) {
        roleService.alter();
        boolean result = roleService.removeById(id);
        if (!result) return Result.error();
        return Result.success();
    }

    @ApiOperation("获取所有角色信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority({'sys:role:adm', 'sys:user:adm'})")
    @GetMapping("/all")
    public Result allRoles() {
        List<Role> list = roleService.list(null);
        return Result.success(list);
    }

    @ApiOperation("获取角色的权限信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        List<Integer> menuIds = roleService.getRoleMenu(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation("设置角色的权限信息")
    @Secured({"ROLE_SUPER", "ROLE_ADMIN"})
    @PreAuthorize("hasAnyAuthority('sys:role:adm')")
    @PostMapping("/roleMenu/{roleId}")
    public Result setRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        Boolean result = roleService.setRoleMenu(roleId, menuIds);
        if (!result) return Result.error();
        return Result.success();
    }
}

