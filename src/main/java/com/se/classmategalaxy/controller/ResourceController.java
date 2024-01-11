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

    @GetMapping("/detail")
    @ApiOperation(notes="用户上传个人资源分享，请求参数为File,返回大小为KB",value="查看资源详细信息接口")
    public HashMap<String,Object> getResourceInfo(@RequestParam int resourceId,@RequestParam int userId) {
        return resourceService.getResourceInfo(resourceId,userId);
    }

    @PostMapping("/upload")
    @ApiOperation(notes="用户上传个人资源分享，请求参数为File,返回大小为KB",value="用户上传资源接口")
    public HashMap<String,Object> uploadFile(@RequestPart MultipartFile file,@RequestParam int userId,@RequestParam int planetId,@RequestParam String introduction) throws IOException {
         return resourceService.uploadFile(file,userId,planetId,introduction);
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

    @GetMapping("/planet")
    @ApiOperation(notes = "根据指定pageNum,pageSize获取指定星球内资源列表", value = "分页获取星球内的所有帖子")
    public HashMap<String,Object> getPlanetResources(@RequestParam int planetId,@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam int userId){
        return resourceService.getPlanetResources(planetId,pageNum,pageSize,userId);
    }


}
