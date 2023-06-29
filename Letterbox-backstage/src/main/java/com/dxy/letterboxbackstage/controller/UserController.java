package com.dxy.letterboxbackstage.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.dto.UserDTO;
import com.dxy.letterboxbackstage.entity.User;
import com.dxy.letterboxbackstage.service.impl.UserServiceImpl;
import com.dxy.letterboxbackstage.utils.UserThreadLocal;
import com.dxy.letterboxbackstage.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-05-09
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    BCryptPasswordEncoder encoder;

    /**
     * 获取全部数据
     * @return
     */
    @ApiOperation("获取所有用户信息")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @GetMapping("/all")
    public Result getAllUsers() {
        List<User> users = userService.list();
        if (users != null && users.size() != 0) {
            List<UserVO> userVos = BeanUtil.copyToList(users, UserVO.class);
            return Result.success(userVos);
        }
        return Result.error();
    }

    /**
     * 更新/添加
     * @param userDto
     * @return
     */
    @ApiOperation("更新/添加用户")
    @Secured({"ROLE_SUPER", "ROLE_USER", "ROLE_COMMON"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm', 'sys:user:self')")
    @PostMapping("/save")
    public Result saveOrUpdate(@RequestBody UserDTO userDto) {
        boolean result = false;
        if (userDto.getId() == null) {
            userService.alter();
            User user = BeanUtil.copyProperties(userDto, User.class, "password");
            user.setPassword(encoder.encode("123456"));
            user.setRoleName("ROLE_COMMON");
            result = userService.saveOrUpdate(user);
        } else {
            userService.alter();
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", userDto.getId());
            User user = BeanUtil.copyProperties(userDto, User.class, "password");
            result = userService.saveOrUpdate(user, wrapper);
        }
        if (result) {
            User user = userService.getByUsername(userDto.getUsername());
            Integer userId = user.getId();
            String roleName = user.getRoleName();
            Boolean userRoleSet = userService.setUserRole(userId, roleName);
            if (userRoleSet) {
                return Result.success();
            }
        }
        return Result.error();
    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @ApiOperation("修改密码")
    @PreAuthorize("hasAnyAuthority('sys:user:self')")
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody HashMap<String, String> map) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", map.get("username"));
        User user = userService.getOne(wrapper);
        if (encoder.matches(map.get("password"), user.getPassword())) {
            String newPassword = map.get("newPassword");
            String encodePassword = encoder.encode(newPassword);
            user.setPassword(encodePassword);
            boolean update = userService.update(user, wrapper);
            if (update) {
                return Result.success();
            } else {
                return Result.error();
            }
        } else {
            return Result.error(CodeEnum.C400, "原密码错误，请重试");
        }
    }

    /**
     * 用户禁用或解禁
     * @param userVo
     * @return
     */
    @ApiOperation("用户解禁/禁用")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @PostMapping("/enable")
    public Result changeEnable(@RequestBody UserVO userVo) {
        User user = BeanUtil.copyProperties(userVo, User.class, "password");
        boolean result = userService.saveOrUpdate(user);
        if (!result) return Result.error();
        if (user.getEnable()) {
            return Result.success("该用户已被启用", null);
        } else {
            return Result.success("该用户已被禁用", null);
        }
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        userService.alter();
        boolean result = userService.removeById(id);
        if (result) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation("批量删除用户")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @PostMapping("/batchDelete")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.alter();
        boolean result = userService.removeByIds(ids);
        if (result) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页显示用户信息")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> result = userService.page(page);
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<User> records = result.getRecords();
        List<UserDTO> userDTOs = BeanUtil.copyToList(records, UserDTO.class);
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", userDTOs);
        return Result.success(map);
    }

    /**
     * 模糊查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param nickname
     * @param address
     * @return
     */
    @ApiOperation("模糊查询用户信息")
    @Secured({"ROLE_SUPER", "ROLE_USER"})
    @PreAuthorize("hasAnyAuthority('sys:user:adm')")
    @GetMapping("/find")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String username,
                       @RequestParam(defaultValue = "") String nickname,
                       @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(username)) {
            wrapper.like("username", username);
        }
        if (StrUtil.isNotBlank(nickname)) {
            wrapper.like("nickname", nickname);
        }
        if (StrUtil.isNotBlank(address)) {
            wrapper.like("address", address);
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> result = userService.page(page, wrapper);
        if (result == null || result.getSize() == 0) {
            return Result.error(CodeEnum.C402, "无记录");
        }
        List<User> records = result.getRecords();
        List<UserDTO> userDtos = BeanUtil.copyToList(records, UserDTO.class);
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotal());
        map.put("page", userDtos);
        return Result.success(map);
    }

    /**
     * 查询当前用户信息
     * @return
     */
    @ApiOperation("获取当前用户信息")
    @PreAuthorize("hasAnyAuthority('sys:user:self')")
    @GetMapping("/local")
    public Result localOne() {
        UserVO userVo = UserThreadLocal.get();
        System.out.println(userVo);
        return Result.success(userVo);
    }

    /**
     * 登录api
     * @param userDto
     * @return
     */
//    @PostMapping("/login")
//    public Result login(@RequestBody UserDto userDto) {
//        Boolean result = userService.login(userDto);
//        if (result) {
//            UserVo userVo = BeanUtil.copyProperties(userDto, UserVo.class, "password");
//            userVo.setToken(JwtUtils.genJwtToken(userVo.getUsername()));
//            return Result.success(userVo);
//        }
//        return Result.error();
//    }

    /**
     * 注册api
     * @param userDto
     * @return
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDto) {
        Boolean result = userService.register(userDto);
        if (result) {
            return Result.success();
        }
        return Result.error();
    }
}

