package com.se.classmategalaxy.service;

import com.se.classmategalaxy.dto.PostUpdateDto;
import com.se.classmategalaxy.dto.ReleasePostDto;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title PostService
 * @description
 * @create 2024/1/10 15:18
 */
public interface PostService {
    HashMap<String, Object> getPlanetPosts(int planetId, int pageNum, int pageSize,int UserId);

    HashMap<String, Object> releasePost(ReleasePostDto releasePostDto);

    HashMap<String, Object> getById(int postId, int userId);

    HashMap<String, Object> getPersonalPosts(int pageNum, int pageSize, int userId);

    HashMap<String, Object> deletePost(int postId);

    HashMap<String, Object> getTopPost();

    HashMap<String, Object> updatePost(PostUpdateDto postUpdateDto);
}
