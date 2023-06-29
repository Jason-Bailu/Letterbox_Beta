package com.dxy.letterboxbackstage.controller;


import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.service.impl.BannerServiceImpl;
import com.dxy.letterboxbackstage.vo.BannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-06-17
 */
@Api(tags = "轮播图接口")
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerServiceImpl bannerService;

    @ApiOperation("获取所有轮播图信息")
    @GetMapping("/all")
    public Result getBanners() {
        List<BannerVO> bannerVos = bannerService.getAllBanners();
        return Result.success(bannerVos);
    }

    @ApiOperation("添加轮播图")
    @Secured({"ROLE_SUPER", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:banner:adm')")
    @PostMapping("/add")
    public Result addBanner(@RequestParam MultipartFile file) {
        Boolean result = bannerService.addBanner(file);
        if (result) return Result.success();
        return Result.error(CodeEnum.C500, "系统出错");
    }

    @ApiOperation("删除轮播图")
    @Secured({"ROLE_SUPER", "ROLE_SHOP"})
    @PreAuthorize("hasAnyAuthority('sys:banner:adm')")
    @PostMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        Boolean result = bannerService.deleteById(id);
        if (result) return Result.success("删除成功", null);
        return Result.error(CodeEnum.C500, "删除失败");
    }
}

