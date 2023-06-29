package com.dxy.letterboxbackstage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.letterboxbackstage.entity.File;
import com.dxy.letterboxbackstage.mapper.FileMapper;
import com.dxy.letterboxbackstage.service.IFileService;
import com.dxy.letterboxbackstage.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author dxy
 * @since 2023-05-15
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Value("${imgs.upload.path}")
    private String imgsUploadPath;

    @Autowired
    FileMapper fileMapper;

    public void alter() {
        fileMapper.alter();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String type = FileUtil.extName(fileName);
        long size = file.getSize();

        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        String url = "";
        String md5 = "";

        //判断文件是否已经存在
        //如果文件已存在，查询md5是否相等
        //(注：这里的md5标识符要用过文件字节码来获取，以保证能判断是否为重复文件)
        //创建文件的唯一标识，以免重复保存同样的文件（使用MD5）
        md5 = SecureUtil.md5(file.getInputStream());
        //查询是否是重复文件，如果是就不进行保存，只是简单进行记录
        com.dxy.letterboxbackstage.entity.File dbFiles = getFileMd5(md5);
        if (dbFiles != null) {
            //文件已存在
            url = dbFiles.getUrl();
        } else {
            if (OSSUtils.uploadImage(file, fileUuid)) {
                url = "https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/" + fileUuid;
            } else {
                return null;
            }
        }

        com.dxy.letterboxbackstage.entity.File dbFile = new com.dxy.letterboxbackstage.entity.File();
        dbFile.setFileName(fileName);
        dbFile.setType(type);
        dbFile.setSize(size/1024);
        dbFile.setUrl(url);
        dbFile.setMd5(md5);
        dbFile.setEnable(true);
        alter();
        if (save(dbFile)) {
            return url;
        } else {
            return null;
        }
    }

    /**
     * 查询重复文件
     * @param md5
     * @return
     */
    private com.dxy.letterboxbackstage.entity.File getFileMd5(String md5) {
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5);
        List<File> list = list(wrapper);
        return list.size() == 0 ? null : list.get(0);
    }

    public void download(String fileUuid, HttpServletResponse response) throws IOException {
        //根据文件唯一标识码进行下载
        java.io.File downloadFile = new java.io.File(imgsUploadPath + fileUuid);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(downloadFile));
        os.flush();
        os.close();
    }
}
