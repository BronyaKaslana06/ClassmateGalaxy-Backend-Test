package com.se.classmategalaxy.service;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title InteractService
 * @description
 * @create 2024/1/10 21:07
 */
public interface InteractService {
    HashMap<String, Object> addPostInteract(int userId, int postId, int clickType);

    HashMap<String, Object> cancelLike(int userId, int postId);

    HashMap<String, Object> cancelCollect(int userId, int postId);

    HashMap<String, Object> getCollectList(int userId,int pageNum,int pageSize);

    HashMap<String, Object> addCommentLike(int userId, int commentId);

    HashMap<String, Object> cancelCommentLike(int userId, int commentId);
}
