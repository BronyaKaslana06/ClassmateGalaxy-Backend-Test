package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.dto.*;
import com.se.classmategalaxy.entity.Comment;
import com.se.classmategalaxy.entity.Post;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.*;
import com.se.classmategalaxy.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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

    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private CollectsMapper collectsMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PlanetMapper planetMapper;

    @Autowired
    private FollowMapper followMapper;

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
            User publisher=userMapper.selectById(post.getAuthorId());
            postWithPublisher.put("author",publisher.getNickname());
            String textResult=removeHtmlTags(post.getContent()).replaceAll("&nbsp;", "");
            postWithPublisher.put("description",textResult.substring(0, Math.min(textResult.length(), 100))+"...");
            postWithPublishers.add(postWithPublisher);
        }
        result.put("status",1);
        result.put("postList",postWithPublishers);
        result.put("totalNum",totalNum);
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

    @Override
    public HashMap<String, Object> getById(int postId, int userId) {
        HashMap<String,Object> result=new HashMap<>();
        Post post=postMapper.selectById(postId);
        if(post!=null){
            result.put("post",post);
            result.put("author",userMapper.selectById(post.getAuthorId()));
            result.put("isLiked",likesMapper.checkIfLiked(postId,userId)>0);
            result.put("isCollected",collectsMapper.checkIfCollected(postId,userId)>0);

            result.put("isFollowed",followMapper.checkIsFollow(new FollowDto(userId,post.getAuthorId()))!=null);
            result.put("deletePermission",post.getAuthorId()==userId);
            //评论与回复列表
            HashMap<String,Object> commentResult=new HashMap<>();
//            List<Comment> commentList=commentMapper.selectRootByPost(postId);
            List<Comment> commentList=new ArrayList<>();
            commentResult.put("CommentNum",commentMapper.selectCountByPost(postId));
            List<CommentInfoDto> commentInfos=new ArrayList<>();

            //当前帖子所有评论
            List<Comment> comments=commentMapper.selectByPost(postId);
            HashMap<Integer,Comment> commentMap=new HashMap<>();
            for(Comment comment : comments){
                commentMap.put(comment.getCommentId(),comment);
                if(comment.getRootParent()==null){
                    commentList.add(comment);
                }
            }

            List<User> userList=userMapper.selectAll();
            HashMap<Integer,User> userMap=new HashMap<>();
            for(User user : userList){
                userMap.put(user.getUserId(),user);
            }

            //全部的回复结果
            List<HashMap<String,Object>> replyResult=new ArrayList<>();
            for(Comment comment : commentList) {
                CommentInfoDto commentInfo = new CommentInfoDto();
                commentInfo.setCommentId(comment.getCommentId());
                commentInfo.setTime(comment.getTime());
                commentInfo.setLikeNum(comment.getLikeNum());
                commentInfo.setPosterId(comment.getUserId());
                commentInfo.setReview(comment.getContent());
//                User poster=userMapper.selectById(comment.getUserId());
                User poster=userMap.get(comment.getUserId());
                commentInfo.setPosterName(poster.getNickname());
                commentInfo.setPosterAvatar(poster.getHeadPhoto());
                commentInfo.setDeletePermission(userId==poster.getUserId());
                commentInfo.setIsLiked(likesMapper.checkIfLikedComment(comment.getCommentId(),userId)>0);
                commentInfos.add(commentInfo);

                HashMap<String,Object> replyInfo=new HashMap<>();
                List<Comment> replyList=commentMapper.selectByRoot(comment.getCommentId());
                List<ReplyInfoDto> replyInfos=new ArrayList<>();
                replyInfo.put("ReplyNum",replyList.size());
                for(Comment reply : replyList) {
                    ReplyInfoDto replyInfoDto = new ReplyInfoDto();
                    replyInfoDto.setCommentId(reply.getCommentId());
                    replyInfoDto.setTime(reply.getTime());
                    replyInfoDto.setLikeNum(reply.getLikeNum());

                    Comment parent=commentMap.get(reply.getParentId());
                    replyInfoDto.setPosterId(parent.getUserId());
                    replyInfoDto.setPosterName(userMap.get(parent.getUserId()).getNickname());
                    replyInfoDto.setRepliedId(reply.getUserId());
                    replyInfoDto.setReview(reply.getContent());
//                    User replier = userMapper.selectById(reply.getUserId());
                    User replier=userMap.get(reply.getUserId());
                    replyInfoDto.setRepliedName(replier.getNickname());

                    replyInfoDto.setRepliedAvatar(replier.getHeadPhoto());
                    replyInfoDto.setDeletePermission(userId == replier.getUserId());
                    replyInfoDto.setIsLiked(likesMapper.checkIfLikedComment(reply.getCommentId(), userId) > 0);
                    replyInfos.add(replyInfoDto);
                }
                replyInfo.put("ReplyList",replyInfos);
                replyResult.add(replyInfo);
            }

            commentResult.put("Replies",replyResult);
            commentResult.put("commentList",commentInfos);

//
//
//
//            // 评论与回复列表
//            HashMap<String, Object> commentResult = new HashMap<>();
////            List<Comment> commentList = commentMapper.selectRootByPost(postId);
//            List<Comment> commentList = commentMapper.selectByPost(postId);
//            commentResult.put("CommentNum", commentMapper.selectCountByPost(postId));
//            List<CommentInfoDto> commentInfos = new ArrayList<>();
//
//            // 获取所有评论用户的ID
//            HashSet<Integer> userIds = (HashSet<Integer>) commentList.stream().map(Comment::getUserId).collect(Collectors.toSet());
//            // 批量查询用户信息
//            List<User> userList=userMapper.selectBatchByIds(userIds);
//            Map<Integer, User> userMap = userList.stream()
//                    .collect(Collectors.toMap(User::getUserId, user -> user));
//
//            // 全部的回复结果
//            List<HashMap<String, Object>> replyResult = new ArrayList<>();
//            for (Comment comment : commentList) {
//                CommentInfoDto commentInfo = new CommentInfoDto();
//                commentInfo.setCommentId(comment.getCommentId());
//                commentInfo.setTime(comment.getTime());
//                commentInfo.setLikeNum(comment.getLikeNum());
//                commentInfo.setPosterId(comment.getUserId());
//                commentInfo.setReview(comment.getContent());
//                User poster = userMap.get(comment.getUserId());
//                commentInfo.setPosterName(poster.getNickname());
//                commentInfo.setPosterAvatar(poster.getHeadPhoto());
//                commentInfo.setDeletePermission(userId == poster.getUserId());
//                commentInfo.setIsLiked(likesMapper.checkIfLikedComment(comment.getCommentId(), userId) > 0);
//                commentInfos.add(commentInfo);
//
//                // 批量查询回复列表
//                List<Comment> replyList = commentMapper.selectByRoot(comment.getCommentId());
//                List<ReplyInfoDto> replyInfos = replyList.stream().map(reply -> {
//                    ReplyInfoDto replyInfoDto = new ReplyInfoDto();
//                    replyInfoDto.setCommentId(reply.getCommentId());
//                    replyInfoDto.setTime(reply.getTime());
//                    replyInfoDto.setLikeNum(reply.getLikeNum());
//                    replyInfoDto.setPosterId(reply.getUserId());
//                    replyInfoDto.setRepliedId(reply.getUserId());
//                    replyInfoDto.setReview(reply.getContent());
//                    User replier = userMap.get(reply.getUserId());
//                    replyInfoDto.setRepliedName(replier.getNickname());
//                    replyInfoDto.setPosterName(poster.getNickname());
//                    replyInfoDto.setPosterAvatar(replier.getHeadPhoto());
//                    replyInfoDto.setDeletePermission(userId == replier.getUserId());
//                    replyInfoDto.setIsLiked(likesMapper.checkIfLikedComment(reply.getCommentId(), userId) > 0);
//                    return replyInfoDto;
//                }).collect(Collectors.toList());
//
//                HashMap<String, Object> replyInfo = new HashMap<>();
//                replyInfo.put("ReplyNum", replyList.size());
//                replyInfo.put("ReplyList", replyInfos);
//                replyResult.add(replyInfo);
//            }
//
//            commentResult.put("Replies", replyResult);
//            commentResult.put("commentList", commentInfos);

            result.put("Comment",commentResult);
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getPersonalPosts(int pageNum, int pageSize, int userId) {
        HashMap<String,Object> result=new HashMap<>();
        int start=(pageNum-1)*pageSize;
        int totalNum=postMapper.selectPersonalCount(userId);
        List<Post> postList=postMapper.selectPageByPersonal(start,pageSize,userId);
        List<HashMap> postWithPublishers=new ArrayList<>();
        for(Post post:postList){
            HashMap<String,Object> postWithPublisher=new HashMap<>();
            postWithPublisher.put("postInfo",post);
            User publisher=userMapper.selectById(userId);
            postWithPublisher.put("author",publisher.getNickname());
            String textResult=removeHtmlTags(post.getContent()).replaceAll("&nbsp;", "");
            postWithPublisher.put("description",textResult.substring(0, Math.min(textResult.length(), 100))+"...");
            postWithPublishers.add(postWithPublisher);
        }
        result.put("status",1);
        result.put("postList",postWithPublishers);
        result.put("totalNum",totalNum);
        return result;
    }

    @Override
    public HashMap<String, Object> deletePost(int postId) {
        HashMap<String,Object> result=new HashMap<>();
        int deleteNum=postMapper.deleteById(postId);
        if(deleteNum>0){
            result.put("status",1);
            result.put("message","删除帖子成功");
        }
        else{
            result.put("status",0);
            result.put("message","删除帖子失败");
        }
        result.put("deleteNum",deleteNum);
        return result;
    }

    @Override
    public HashMap<String, Object> getTopPost() {
        HashMap<String,Object> result=new HashMap<>();
        List<Post> postList=postMapper.getTopPost(7);
        List<PostBriefDto> postBriefs=new ArrayList<>();
        for(Post post : postList){
            PostBriefDto postBrief = new PostBriefDto();
            postBrief.setTitle(post.getTitle());
            postBrief.setPostId(post.getPostId());
            postBrief.setUserId(post.getAuthorId());
            postBrief.setViewNum(post.getViewNum());
            postBrief.setAuthorName(userMapper.selectById(post.getAuthorId()).getNickname());
            postBrief.setPlanetId(post.getPlanetId());
            postBrief.setPlanetName(planetMapper.selectById(post.getPlanetId()).getPlanetName());
            postBriefs.add(postBrief);
        }
        result.put("postList",postBriefs);
        result.put("status",1);
        result.put("message","获取成功");
        return result;
    }

    @Override
    public HashMap<String, Object> updatePost(PostUpdateDto postUpdateDto) {
        HashMap<String,Object> result=new HashMap<>();
        if(postMapper.updatePost(postUpdateDto)>0){
            result.put("status",1);
            result.put("message","编辑更新成功");
        }else{
            result.put("status",0);
            result.put("message","编辑更新失败");
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
