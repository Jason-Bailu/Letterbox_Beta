package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.dxy.letterboxbackstage.entity.Banner;
import com.dxy.letterboxbackstage.mapper.BannerMapper;
import com.dxy.letterboxbackstage.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.utils.OSSUtils;
import com.dxy.letterboxbackstage.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 轮播图表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-06-17
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Autowired
    BannerMapper bannerMapper;

    public void alter() {
        bannerMapper.alter();
    }

    public List<BannerVO> getAllBanners() {
        List<Banner> banners = list();
        List<BannerVO> bannerVos = new ArrayList<>();
        banners.forEach(banner -> {
            BannerVO bannerVo = BeanUtil.copyProperties(banner, BannerVO.class);
            bannerVos.add(bannerVo);
        });
        return bannerVos;
    }

    public Boolean addBanner(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String url = "";
        if (OSSUtils.uploadBanner(file, fileName)) {
            url = "https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/" + fileName;
        } else {
            return false;
        }
        Banner banner = new Banner();
        banner.setName(fileName);
        banner.setUrl(url);
        alter();
        if (save(banner)) return true;
        return false;
    }

    public Boolean deleteById(Integer id) {
        return removeById(id);
    }
}
