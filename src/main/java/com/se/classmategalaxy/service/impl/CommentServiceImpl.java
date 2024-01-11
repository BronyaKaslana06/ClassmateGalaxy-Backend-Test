package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.entity.Comment;
import com.se.classmategalaxy.mapper.CommentMapper;
import com.se.classmategalaxy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title CommentServiceImpl
 * @description
 * @create 2024/1/10 23:34
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public HashMap<String, Object> addComment(Comment comment) {
        HashMap<String,Object> result=new HashMap<>();
        if(commentMapper.addComment(comment)>0){
            result.put("commentId",comment.getCommentId());
            result.put("commentTime",commentMapper.selectById(comment.getCommentId()).getTime());
            result.put("status",1);
            result.put("message","发布成功");
        }else{
            result.put("status",0);
            result.put("message","评论发布失败");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> deleteComment(int commentId) {
        HashMap<String,Object> result=new HashMap<>();
        int deleteNum=commentMapper.deleteCommentById(commentId);
        if(deleteNum>0){
            result.put("status",1);
            result.put("message","删除评论成功");
        }
        else{
            result.put("status",0);
            result.put("message","删除评论失败");
        }
        result.put("deleteNum",deleteNum);
        return result;
    }
}
