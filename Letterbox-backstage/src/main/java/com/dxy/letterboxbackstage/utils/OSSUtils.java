package com.dxy.letterboxbackstage.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author: JasonD
 * @date: 2023/6/3 20:38
 * @Description: OSS文件上传工具类
 */
public class OSSUtils {
    @Value("${imgs.upload.path}")
    private static String imgsUploadPath;
    @Value("${texts.upload.path}")
    private static String textsUploadPath;
    private final static String endpoint = "endpoint";
    private final static String accessKeyId = "aliapikey";
    private final static String accessKeySecret = "accessKeySecret";
    private final static String bucketName = "bucketName";

    //图片上传
    public static Boolean uploadImage(MultipartFile file, String fileUuid) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "imgs/"+fileUuid, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    //图片删除: 删除的操作属于逻辑删除，就不使用该方法
    public static Boolean deleteImage(String fileUuid) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, "images/"+fileUuid);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    //文本上传
    public static Boolean uploadText(String mailContent, String fileUuid) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(mailContent.getBytes());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "texts/"+fileUuid, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    //轮播图上传
    public static Boolean uploadBanner(MultipartFile file, String bannerFile) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "banners/"+bannerFile, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
