package com.se.classmategalaxy.service;

import com.se.classmategalaxy.entity.Evaluate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    HashMap<String,Object> uploadFile(MultipartFile localFile, int userId, int planetId,String introduction) throws IOException;

    void downloadFile(int resourceId, HttpServletResponse response) throws IOException;

    HashMap<String, Object> uploadPhoto(MultipartFile file) throws IOException;

    HashMap<String, Object> getPlanetResources(int planetId, int pageNum, int pageSize, int userId);

    HashMap<String, Object> getResourceInfo(int resourceId, int userId);

    HashMap<String, Object> evaluateResource(Evaluate evaluate);
}
