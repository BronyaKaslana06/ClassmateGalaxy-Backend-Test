package com.se.classmategalaxy.service;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title RecommendService
 * @description
 * @create 2024/1/11 11:18
 */
public interface RecommendService {
    HashMap<String,Object> recommendPlanetsForUser(Integer userId);

}
