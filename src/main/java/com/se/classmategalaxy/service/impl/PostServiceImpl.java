package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.entity.Post;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.PostMapper;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author wyx20
 * @version 1.0
 * @title PostServiceImpl
 * @description
 * @create 2024/1/10 15:19
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public HashMap<String, Object> getPlanetPosts(int planetId, int pageNum, int pageSize,int userId) {
        HashMap<String,Object> result=new HashMap<>();
        int start=(pageNum-1)*pageSize;
        int totalNum=postMapper.selectPlanetCount(planetId);
        List<Post> postList=postMapper.selectPageByPlanet(planetId,start,pageSize,userId);
        List<HashMap> postWithPublishers=new ArrayList<>();
        for(Post post:postList){
            HashMap<String,Object> postWithPublisher=new HashMap<>();
            postWithPublisher.put("postInfo",post);
            User publisher=userMapper.selectById(userId);
            postWithPublisher.put("author",publisher.getNickname());
            String textResult=removeHtmlTags(post.getContent());
            postWithPublisher.put("description",textResult.substring(0, Math.min(textResult.length(), 100))+"...");
            postWithPublishers.add(postWithPublisher);
        }
        result.put("status",1);
        result.put("postList",postWithPublishers);
        result.put("pages",totalNum/pageSize+1);
        return result;
    }

    @Override
    public HashMap<String, Object> releasePost(ReleasePostDto releasePostDto) {
        HashMap<String,Object> result=new HashMap<>();
        Integer isRelease=postMapper.releasePost(releasePostDto);
        if(isRelease>=1){
            result.put("status",1);
            result.put("message","发布成功");
        }
        else{
            result.put("status",0);
            result.put("message","发布失败");
        }
        return result;
    }


    private static String removeHtmlTags(String input) {
        // 定义正则表达式，匹配任何以<开头，以>结尾的内容
        String regex = "<[^>]*>";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 使用正则表达式匹配器进行匹配
        Matcher matcher = pattern.matcher(input);
        // 替换匹配到的内容为空字符串
        String result = matcher.replaceAll("");
        return result;
    }

}
