package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.service.InteractService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title InteractController
 * @description
 * @create 2024/1/10 20:55
 */
@RestController
@RequestMapping("/interact")
public class InteractController {
    @Autowired
    InteractService interactService;
    @PostMapping("/post")
    @ApiOperation(notes = "clickType 1为点赞2为收藏3为浏览", value = "与帖子进行互动(点赞、收藏、浏览）")
    public HashMap<String,Object> addPostInteract(@RequestParam int userId,@RequestParam int postId,@RequestParam int clickType){
        return interactService.addPostInteract(userId,postId,clickType);
    }

    @PostMapping("/comment/like")
    @ApiOperation(notes = "点赞某条评论", value = "点赞评论")
    public HashMap<String,Object> addCommentLike(@RequestParam int userId,@RequestParam int commentId){
        return interactService.addCommentLike(userId,commentId);
    }

    @DeleteMapping("/comment/cancelLike")
    @ApiOperation(notes = "取消对评论的点赞，并更新当前点赞数", value = "取消对评论的点赞")
    public HashMap<String,Object> cancelCommentLike(@RequestParam int userId,@RequestParam int commentId){
        return interactService.cancelCommentLike(userId,commentId);
    }

    @DeleteMapping("/post/cancelLike")
    @ApiOperation(notes = "取消对帖子的点赞，并更新当前点赞数", value = "取消对帖子的点赞")
    public HashMap<String,Object> cancelLike(@RequestParam int userId,@RequestParam int postId){
        return interactService.cancelLike(userId,postId);
    }

    @DeleteMapping("/post/cancelCollect")
    @ApiOperation(notes = "取消对帖子的收藏，并更新当前收藏数", value = "取消对帖子的收藏")
    public HashMap<String,Object> cancelCollect(@RequestParam int userId,@RequestParam int postId){
        return interactService.cancelCollect(userId,postId);
    }

    @GetMapping("/post/getCollectList")
    @ApiOperation(notes = "获取用户id为userId的人的收藏列表", value = "获取个人收藏列表")
    public HashMap<String,Object> getCollectList(@RequestParam int userId,@RequestParam int pageNum,@RequestParam int pageSize){
        return interactService.getCollectList(userId,pageNum,pageSize);
    }



}
