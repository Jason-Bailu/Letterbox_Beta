package com.dxy.letterboxbackstage.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.service.impl.FileServiceImpl;
import com.dxy.letterboxbackstage.utils.OSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-05-15
 */
@Api(tags = "图片接口")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileServiceImpl fileService;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("图片上传")
    @PreAuthorize("hasAnyAuthority('sys:file:pic', 'sys:user:self')")
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String url = fileService.uploadFile(file);
        if (url != null) return Result.success(url);
        return Result.error();
    }

    /**
     * 文件下载
     * @return
     */
    @ApiOperation("图片下载")
    @GetMapping("/download/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        fileService.download(fileUuid, response);
    }

    /**
     * 获取文件分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("图片分页信息")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:pic')")
    @GetMapping("/page")
    public Result getFilePage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<com.dxy.letterboxbackstage.entity.File> filePage = new Page<>(pageNum, pageSize);
        Page<com.dxy.letterboxbackstage.entity.File> page = fileService.page(filePage);
        Map<String, Object> res = new HashMap<>();
        res.put("total", page.getTotal());
        res.put("page", page.getRecords());
        return Result.success(res);
    }

    /**
     * 根据id删除文件
     * @param id
     * @return
     */
    @ApiOperation("根据id删除图片")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:pic')")
    @DeleteMapping("/delete")
    public Result deleteById(@RequestParam Integer id) {
        boolean result = fileService.removeById(id);
        if (result) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation("批量删除图片")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:pic')")
    @PostMapping ("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        boolean result = fileService.removeByIds(ids);
        if (result == true) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("图片开启/禁用")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:pic')")
    @PostMapping("/enable")
    public Result updateEnable(@RequestBody com.dxy.letterboxbackstage.entity.File file) {
        boolean result = fileService.updateById(file);
        if (result == true) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("图片的模糊查询")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:pic')")
    @GetMapping("/find")
    public Result findFiles(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String filename) {
        QueryWrapper<com.dxy.letterboxbackstage.entity.File> wrapper = new QueryWrapper<>();
        if (!filename.isEmpty() || !filename.isEmpty()) {
            wrapper.like("file_name", filename);
        }
        Page<com.dxy.letterboxbackstage.entity.File> filePage = new Page<>(pageNum, pageSize);
        Page<com.dxy.letterboxbackstage.entity.File> page = fileService.page(filePage, wrapper);
        Map<String, Object> res = new HashMap<>();
        res.put("total", page.getTotal());
        res.put("page", page.getRecords());
        return Result.success(res);
    }
}

