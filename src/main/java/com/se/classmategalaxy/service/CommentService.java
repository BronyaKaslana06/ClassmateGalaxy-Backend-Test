package com.se.classmategalaxy.service;

import com.se.classmategalaxy.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title CommentService
 * @description
 * @create 2024/1/10 23:34
 */
public interface CommentService {
    HashMap<String, Object> addComment(Comment comment);

    HashMap<String, Object> deleteComment(int commentId);
}
