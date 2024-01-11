package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Likes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author wyx20
 * @version 1.0
 * @title LikesMapper
 * @description
 * @create 2024/1/10 21:22
 */
@Mapper
public interface LikesMapper extends BaseMapper<Likes> {

    @Select("SELECT count(*) from likes where user_id=#{userId} and content_type=1 and content_id=#{postId} limit 1")
    int checkIfLiked(int userId, int postId);

    @Insert("INSERT INTO likes (user_id,content_id,content_type) values (#{userId},#{postId},1)")
    void addPostLikes(int userId, int postId);

    @Delete("DELETE from likes where user_id=#{userId} and content_id=#{postId} and content_type=1")
    void cancelLike(int userId, int postId);

    @Select("SELECT count(*) from likes where user_id=#{userId} and content_type=2 and content_id=#{commentId} limit 1")
    int checkIfLikedComment(Integer commentId, int userId);

    @Insert("INSERT INTO likes (user_id,content_id,content_type) values (#{commentId},#{commentId},2)")
    void addCommentLikes(int userId, int commentId);

    @Delete("DELETE from likes where user_id=#{userId} and content_id=#{commentId} and content_type=2")
    void cancelCommentLike(int userId, int commentId);
}
