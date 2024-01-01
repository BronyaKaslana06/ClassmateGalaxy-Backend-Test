package com.se.classmategalaxy.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title ResourceService
 * @description
 * @create 2024/1/1 22:48
 */
public interface ResourceService {

    public HashMap<String,Object> uploadFile(MultipartFile localFile, int userId, int planetId) throws IOException;
}
