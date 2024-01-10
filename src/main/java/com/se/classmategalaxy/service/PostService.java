package com.se.classmategalaxy.service;

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
}
