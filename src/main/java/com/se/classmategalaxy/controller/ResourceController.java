package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title SourceController
 * @description
 * @create 2024/1/1 20:32
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    @ApiOperation(notes="用户上传个人资源分享，请求参数为File,返回大小为KB",value="用户上传资源接口")
    public HashMap<String,Object> uploadFile(@RequestPart MultipartFile file,@RequestParam int userId,@RequestParam int planetId) throws IOException {
         return resourceService.uploadFile(file,userId,planetId);
    }

    @GetMapping("/download")
    @ApiOperation(notes="请求参数为resourceId",value="用户下载资源接口")
    public void downloadFile(@RequestParam int resourceId, HttpServletResponse response) throws IOException {
        resourceService.downloadFile(resourceId, response);
    }

    @PostMapping("/uploadPhoto")
    @ApiOperation(notes="用户上传个人图片，请求参数为File,返回大小为KB",value="用户上传图片接口")
    public HashMap<String,Object> uploadPhoto(@RequestPart MultipartFile file) throws IOException {
        return resourceService.uploadPhoto(file);
    }
}
