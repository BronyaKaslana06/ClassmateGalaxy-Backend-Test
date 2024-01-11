package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.dto.PostUpdateDto;
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
    @ApiOperation(notes = "根据指定pageNum,pageSize获取指定星球内帖子列表", value = "分页获取星球内的所有帖子")
    public HashMap<String,Object> getPlanetPosts(@RequestParam int planetId,@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam int userId){
        return postService.getPlanetPosts(planetId,pageNum,pageSize,userId);
    }

    @GetMapping("/personal")
    @ApiOperation(notes = "根据指定pageNum,pageSize获取指定user帖子列表", value = "分页获取个人的所有帖子")
    public HashMap<String,Object> getPersonalPosts(@RequestParam int pageNum,@RequestParam int pageSize,@RequestParam int userId){
        return postService.getPersonalPosts(pageNum,pageSize,userId);
    }

    @PostMapping("/release")
    @ApiOperation(notes = "返回状态信息", value = "发布帖子")
    public HashMap<String,Object> releasePost(@RequestBody ReleasePostDto releasePostDto){
        return postService.releasePost(releasePostDto);
    }

    @DeleteMapping("/delete")
    @ApiOperation(notes = "返回状态信息", value = "删除帖子")
    public HashMap<String,Object> deletePost(@RequestParam int postId){
        return postService.deletePost(postId);
    }

    @GetMapping("/getById")
    @ApiOperation(notes = "返回状态信息", value = "获取某个帖子的全部信息")
    public HashMap<String,Object> getById(@RequestParam int postId,@RequestParam int userId){
        return postService.getById(postId,userId);
    }

    @GetMapping("/getTop")
    @ApiOperation(notes = "返回帖子基本信息", value = "获取排名前七的帖子基本信息")
    public HashMap<String,Object> getTopPost(){
        return postService.getTopPost();
    }

    @PutMapping("/update")
    @ApiOperation(notes = "需要更新的字段传入为更新后，不需要更新的保持原样即可", value = "编辑更新帖子")
    public HashMap<String,Object> updatePost(@RequestBody PostUpdateDto postUpdateDto){
        return postService.updatePost(postUpdateDto);
    }


}
