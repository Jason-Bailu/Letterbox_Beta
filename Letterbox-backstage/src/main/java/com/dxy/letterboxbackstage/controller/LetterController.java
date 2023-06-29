package com.dxy.letterboxbackstage.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.dto.LetterDTO;
import com.dxy.letterboxbackstage.entity.Good;
import com.dxy.letterboxbackstage.entity.Letter;
import com.dxy.letterboxbackstage.service.impl.GoodServiceImpl;
import com.dxy.letterboxbackstage.service.impl.LetterServiceImpl;
import com.dxy.letterboxbackstage.vo.GoodVO;
import com.dxy.letterboxbackstage.vo.LetterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 信件表 前端控制器
 * </p>
 *
 * @author dxy
 * @since 2023-06-04
 */
@Api(tags = "信件接口")
@RestController
@RequestMapping("/letter")
public class LetterController {
    @Autowired
    private LetterServiceImpl letterService;

    @Autowired
    private GoodServiceImpl goodService;

    @ApiOperation("信件分页")
    @GetMapping("/page")
    public Result getLetterPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username) {
        Page<Letter> letterPage = new Page<>(pageNum, pageSize);
        Page<Letter> page = null;
        if (StrUtil.isNotBlank(username)) {
            QueryWrapper<Letter> wrapper = new QueryWrapper<>();
            wrapper.like("username", username);
            page = letterService.page(letterPage, wrapper);
        } else {
            page = letterService.page(letterPage);
        }
        List<Letter> records = page.getRecords();
        List<LetterVO> letterVOS = new ArrayList<>();
        records.stream().forEach(letter -> {
            LetterVO letterVo = BeanUtil.copyProperties(letter, LetterVO.class, "createTime");
            LocalDate date = letter.getCreateTime().toLocalDate();
            letterVo.setDate(date);
            GoodVO paperGood = BeanUtil.copyProperties(goodService.getOne(new QueryWrapper<Good>().eq("id", letterVo.getPaperId())), GoodVO.class);
            GoodVO coverGood = BeanUtil.copyProperties(goodService.getOne(new QueryWrapper<Good>().eq("id", letterVo.getCoverId())), GoodVO.class);
            GoodVO inkGood = BeanUtil.copyProperties(goodService.getOne(new QueryWrapper<Good>().eq("id", letterVo.getInkId())), GoodVO.class);
            List<GoodVO> goods = new ArrayList<>(10);
            goods.add(paperGood);
            goods.add(coverGood);
            goods.add(inkGood);
            letterVo.setGoods(goods);
            letterVOS.add(letterVo);
        });
        Map<String, Object> res = new HashMap<>();
        res.put("total", page.getTotal());
        res.put("page", letterVOS);
        return Result.success(res);
    }

    @ApiOperation("添加信件")
    @Secured({"ROLE_SUPER", "ROLE_FILE"})
    @PreAuthorize("hasAnyAuthority('sys:file:txt')")
    @PostMapping("/add")
    public Result addLetter(@RequestBody LetterDTO letterDto) {
        Boolean result = letterService.addLetter(letterDto);
        if (result) return Result.success();
        return Result.error();
    }

    @ApiOperation("上传信件")
    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('sys:user:self')")
    public Result uploadLetter(@RequestBody LetterDTO letterDto) {
        Good paper = goodService.getOne(new QueryWrapper<Good>().eq("id", letterDto.getPaperId()));
        Good cover = goodService.getOne(new QueryWrapper<Good>().eq("id", letterDto.getCoverId()));
        if (cover.getRepository() < 1 || paper.getRepository() < 1) return Result.error(CodeEnum.C404, "资源有限，请换别的配置试试");
        if (paper.getId() != 6 || cover.getId() != 12) {
            paper.setRepository(paper.getRepository()-1);
            goodService.saveOrUpdate(paper, new QueryWrapper<Good>().eq("id", paper.getId()));
            cover.setRepository(cover.getRepository()-1);
            goodService.saveOrUpdate(cover, new QueryWrapper<Good>().eq("id", cover.getId()));
        }
        Boolean result = letterService.addLetter(letterDto);
        if (result) return Result.success();
        return Result.error();
    }
}

