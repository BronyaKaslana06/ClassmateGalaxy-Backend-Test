package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.entity.Collects;
import com.se.classmategalaxy.entity.Post;
import com.se.classmategalaxy.mapper.*;
import com.se.classmategalaxy.service.CommentService;
import com.se.classmategalaxy.service.InteractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title InteractServiceImpl
 * @description
 * @create 2024/1/10 21:08
 */
@Service
public class InteractServiceImpl implements InteractService {
    @Autowired
    private LikesMapper likesMapper;
    @Autowired
    private CollectsMapper collectsMapper;
    @Autowired
    private ViewsMapper viewsMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public HashMap<String, Object> addPostInteract(int userId, int postId, int clickType) {
        HashMap<String,Object> result=new HashMap<>();
        if(clickType==1){
            if(likesMapper.checkIfLiked(userId,postId)>0){
                result.put("status",0);
                result.put("message","已点赞，无法重复点赞");
            }
            else{
                likesMapper.addPostLikes(userId,postId);
                //新增点赞数并更新返回likeNum
                postMapper.addLikesNum(postId);
                result.put("likeNum",postMapper.selectById(postId).getLikeNum());
                result.put("status",1);
                result.put("message","新增点赞成功");
            }
        }
        else if(clickType==2){
            if(collectsMapper.checkIfCollected(userId,postId)>0){
                result.put("status",0);
                result.put("message","已收藏，无法重复收藏");
            }
            else{
                collectsMapper.addPostCollects(userId,postId);
                //新增收藏数并更新返回likeNum
                postMapper.addCollectsNum(postId);
                result.put("collectNum",postMapper.selectById(postId).getCollectNum());
                result.put("status",1);
                result.put("message","新增收藏成功");
            }
        }
        else if(clickType==3){
            //浏览记录貌似不用存入数据库
            //新增浏览数并更新返回likeNum
            postMapper.addViewsNum(postId);
            result.put("viewNum",postMapper.selectById(postId).getViewNum());
            result.put("status",1);
            result.put("message","新增浏览成功");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> cancelLike(int userId, int postId) {
        HashMap<String,Object> result=new HashMap<>();
        likesMapper.cancelLike(userId,postId);
        postMapper.decreaseLikeNum(postId);
        result.put("likeNum",postMapper.selectById(postId).getLikeNum());
        result.put("status",1);
        result.put("message","取消点赞成功");
        return result;
    }

    @Override
    public HashMap<String, Object> cancelCollect(int userId, int postId) {
        HashMap<String,Object> result=new HashMap<>();
        collectsMapper.cancelCollect(userId,postId);
        postMapper.decreaseCollectNum(postId);
        result.put("collectNum",postMapper.selectById(postId).getCollectNum());
        result.put("status",1);
        result.put("message","取消收藏成功");
        return result;
    }

    @Override
    public HashMap<String, Object> getCollectList(int userId,int pageNum,int pageSize) {
        HashMap<String,Object> result=new HashMap<>();
        int start=(pageNum-1)*pageSize;
        Integer totalNum=collectsMapper.selectUserCount(userId);
        List<Collects> collectsList=collectsMapper.selectByUserId(userId,start,pageSize);
        List<HashMap<String,Object>> collectsResult=new ArrayList<>();
        for(Collects collect : collectsList){
            HashMap<String,Object> collectResult=new HashMap<>();
            collectResult.put("collectTime",collect.getTime());
            collectResult.put("collectId",collect.getCollectId());
            collectResult.put("post",postMapper.selectById(collect.getContentId()));
            collectsResult.add(collectResult);
        }
        result.put("collect",collectsResult);
        result.put("totalNum",totalNum);
        result.put("status",1);
        result.put("message","获取收藏列表成功");
        return result;
    }

    @Override
    public HashMap<String, Object> addCommentLike(int userId, int commentId) {
        HashMap<String,Object> result=new HashMap<>();
        if(likesMapper.checkIfLikedComment(userId,commentId)>0){
            result.put("status",0);
            result.put("message","已点赞，无法重复点赞");
        }
        else{
            likesMapper.addCommentLikes(userId,commentId);
            //新增点赞数并更新返回likeNum
            commentMapper.addLikesNum(commentId);
            result.put("likeNum",commentMapper.selectById(commentId).getLikeNum());
            result.put("status",1);
            result.put("message","新增点赞成功");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> cancelCommentLike(int userId, int commentId) {
        HashMap<String,Object> result=new HashMap<>();
        likesMapper.cancelCommentLike(userId,commentId);
        commentMapper.decreaseLikeNum(commentId);
        result.put("likeNum",commentMapper.selectById(commentId).getLikeNum());
        result.put("status",1);
        result.put("message","取消点赞成功");
        return result;
    }
}
