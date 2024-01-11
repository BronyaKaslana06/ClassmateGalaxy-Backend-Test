package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.entity.Comment;
import com.se.classmategalaxy.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title CommentController
 * @description
 * @create 2024/1/10 23:25
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/post")
    @ApiOperation(notes = "发布评论或者回复，保存相关信息", value = "发布评论或回复")
    public HashMap<String,Object> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @DeleteMapping("/delete")
    @ApiOperation(notes = "删除评论或者回复的接口", value = "删除评论或回复")
    public HashMap<String,Object> deleteComment(@RequestParam int commentId){
        return commentService.deleteComment(commentId);
    }
}
