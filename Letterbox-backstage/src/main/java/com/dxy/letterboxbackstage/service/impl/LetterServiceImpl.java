package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.dto.LetterDTO;
import com.dxy.letterboxbackstage.entity.Letter;
import com.dxy.letterboxbackstage.mapper.LetterMapper;
import com.dxy.letterboxbackstage.service.ILetterService;
import com.dxy.letterboxbackstage.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信件表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-06-04
 */
@Service
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements ILetterService {
    @Value("${texts.upload.path}")
    private String textsUploadPath;

    @Autowired
    private LetterMapper letterMapper;

    public void alter() {
        letterMapper.alter();
    }

    public Boolean addLetter(LetterDTO letterDto) {
        alter();
        Letter letter = BeanUtil.copyProperties(letterDto, Letter.class);
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + "txt";
        String url = textsUploadPath+fileUuid;
        String mailContent = letter.toString();
        Boolean uploadResult = OSSUtils.uploadText(mailContent, fileUuid);
        letter.setUrl(url);
        return save(letter) && uploadResult;
    }
}
