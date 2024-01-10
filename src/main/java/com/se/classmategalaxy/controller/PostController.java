package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title PostController
 * @description
 * @create 2024/1/10 13:30
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;
    @GetMapping("/planet")
    @ApiOperation(notes = "返回指定页数的所有帖子全部信息", value = "分页获取星球内的所有帖子")
    public HashMap<String,Object> getPlanetPosts(@RequestParam int planetId,@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam int userId){
        return postService.getPlanetPosts(planetId,pageNum,pageSize,userId);
    }

    @PostMapping("/release")
    @ApiOperation(notes = "返回状态信息", value = "发布帖子")
    public HashMap<String,Object> releasePost(@RequestBody ReleasePostDto releasePostDto){
        return postService.releasePost(releasePostDto);
    }


}
