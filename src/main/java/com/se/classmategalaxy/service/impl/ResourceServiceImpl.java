package com.se.classmategalaxy.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.IOUtils;
import com.se.classmategalaxy.entity.Post;
import com.se.classmategalaxy.entity.Resource;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.ResourceMapper;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public HashMap<String, Object> uploadFile(MultipartFile localFile, int userId, int planetId,String introduction) throws IOException {
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
            uploadResource.setIntroduction(introduction);
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

    @Override
    public void downloadFile(int resourceId, HttpServletResponse response) throws IOException {
        Resource downloadResource=resourceMapper.selectById(resourceId);
        String key=downloadResource.getResourceKey();
        // 方法1 获取下载输入流
        String bucketName = "se2023-1320924912";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 读取文件内容
        byte[] fileContent = IOUtils.toByteArray(cosObjectInput);

        // 关闭输入流
        cosObjectInput.close();

        //更新下载次数+1
        resourceMapper.addDownloadCount(resourceId);

        // 设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String encodedFilename = URLEncoder.encode(downloadResource.getName(), StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename="+encodedFilename); // 设置下载文件的名称
        // 将文件内容写入响应流
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(fileContent);
            outputStream.flush();
        } catch (IOException e) {
            // 处理异常
        }
    }

    @Override
    public HashMap<String, Object> uploadPhoto(MultipartFile photo) throws IOException {
        HashMap<String,Object> result=new HashMap<>();
        // 获取文件的内容类型
        String contentType = photo.getContentType();
        if (contentType != null && contentType.startsWith("image/")) {
            // 获取文件名称
            String fileName = photo.getOriginalFilename();
            result.put("fileName", fileName);
            // 获取文件大小，以字节为单位
            long fileSizeInBytes = photo.getSize();
            // 将字节数转换为千字节（KB）
            double fileSizeInKB = (double) fileSizeInBytes / 1024;
            result.put("fileSize", fileSizeInKB);

            // 指定文件将要存放的存储桶
            String bucketName = "se2023-1320924912";
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            // 生成唯一标识
            String uniqueIdentifier = UUID.randomUUID().toString();
            String key = "photo/"+uniqueIdentifier+"_"+fileName;
            // 创建临时文件
            File tempFile = File.createTempFile("temp", null);
            // 将MultipartFile的内容写入临时文件
            photo.transferTo(tempFile);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,tempFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            result.put("status",1);
           result.put("photoUrl",cosClient.getObjectUrl(bucketName, key));
            return result;

        } else {
            result.put("message", "文件类型不为图片或图片为空");
            result.put("status", "0");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getPlanetResources(int planetId, int pageNum, int pageSize, int userId) {
        HashMap<String,Object> result=new HashMap<>();
        int start=(pageNum-1)*pageSize;
        int totalNum=resourceMapper.selectPlanetCount(planetId);
        List<Resource> resourceList=resourceMapper.selectPageByPlanet(planetId,start,pageSize,userId);
        List<HashMap> resourceWithPublishers=new ArrayList<>();
        for(Resource resource:resourceList){
            HashMap<String,Object> resourceWithPublisher=new HashMap<>();
            resourceWithPublisher.put("resourceInfo",resource);
            User publisher=userMapper.selectById(userId);
            resourceWithPublisher.put("publisher",publisher.getNickname());
            String introduction=resource.getIntroduction();
            if(introduction!=null) {
                if(introduction.length()<100){
                    resourceWithPublisher.put("introduction", introduction.substring(0, introduction.length()));
                }
                else {
                    resourceWithPublisher.put("introduction", introduction.substring(0, 100) + "...");
                }
            }
            else{
                resourceWithPublisher.put("introduction", "作者很懒，没有写简介哦");
            }
            resourceWithPublishers.add(resourceWithPublisher);
        }
        result.put("status",1);
        result.put("resourceList",resourceWithPublishers);
        result.put("totalNum",totalNum);
        return result;
    }

    @Override
    public HashMap<String, Object> getResourceInfo(int resourceId, int userId) {
        HashMap<String,Object> result=new HashMap<>();
        Resource resource=resourceMapper.selectById(resourceId);
        result.put("resource",resource);
        User publisher=userMapper.selectById(resource.getUserId());
        List<String> tagsList = publisher.getPersonalTag() != null && !publisher.getPersonalTag().isEmpty() ?
                Arrays.asList(publisher.getPersonalTag().split(",")) :
                Collections.emptyList();

        publisher.setTagList(tagsList);
        result.put("publisher",publisher);
        result.put("status",1);
        result.put("message","获取资源信息成功");
        return result;
    }
}
