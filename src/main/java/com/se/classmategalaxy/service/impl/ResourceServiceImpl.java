package com.se.classmategalaxy.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.se.classmategalaxy.entity.Resource;
import com.se.classmategalaxy.mapper.ResourceMapper;
import com.se.classmategalaxy.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author wyx20
 * @version 1.0
 * @title ResourceServiceImpl
 * @description
 * @create 2024/1/1 22:57
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
    String tmpSecretId = "AKID6RdaPl4INJ5iCUQC1rY4QZSDqIXXddU7";
    String tmpSecretKey = "MWt691hIwbFoHxZ7hDg6d6Uo2AGSWQ9g";
    String sessionToken = "";
    BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
    // 2 设置 bucket 的地域
    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
    Region region = new Region("ap-shanghai"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
    ClientConfig clientConfig = new ClientConfig(region);
    // 3 生成 cos 客户端
    COSClient cosClient = new COSClient(cred, clientConfig);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public HashMap<String, Object> uploadFile(MultipartFile localFile, int userId, int planetId) throws IOException {
        HashMap<String,Object> result=new HashMap<>();
        if (!localFile.isEmpty()) {
            // 获取文件名称
            String fileName = localFile.getOriginalFilename();
            result.put("fileName", fileName);
            // 获取文件大小，以字节为单位
            long fileSizeInBytes = localFile.getSize();
            // 将字节数转换为千字节（KB）
            double fileSizeInKB = (double) fileSizeInBytes / 1024;
            result.put("fileSize", fileSizeInKB);

            // 指定文件将要存放的存储桶
            String bucketName = "se2023-1320924912";
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            // 生成唯一标识
            String uniqueIdentifier = UUID.randomUUID().toString();
            String key = "resource/"+uniqueIdentifier+"_"+fileName;
            // 创建临时文件
            File tempFile = File.createTempFile("temp", null);
            // 将MultipartFile的内容写入临时文件
            localFile.transferTo(tempFile);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,tempFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            result.put("status",1);
            Resource uploadResource=new Resource();
            uploadResource.setName(fileName);
            uploadResource.setSize(fileSizeInKB);
            uploadResource.setResourceKey(key);
            uploadResource.setUserId(userId);
            uploadResource.setPlanetId(planetId);
            resourceMapper.uploadFile(uploadResource);
            int resourceId=uploadResource.getResourceId();
            result.put("resourceId",resourceId);
            result.put("resource",resourceMapper.selectById(resourceId));
            return result;

        } else {
            result.put("message", "文件为空");
            result.put("status", "0");
        }
        return result;
    }
}
