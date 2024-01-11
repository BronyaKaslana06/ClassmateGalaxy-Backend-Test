package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CommentMapper
 * @description
 * @create 2024/1/10 23:38
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Insert("INSERT INTO comment (content,parent_id,root_parent,post_id,user_id) VALUES (#{content},#{parentId},#{rootParent},#{postId},#{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "commentId")
    int addComment(Comment comment);

    @Select("SELECT * from comment where post_id=#{postId} and root_parent is null")
    List<Comment> selectRootByPost(int postId);

    @Update("UPDATE comment SET like_num = like_num + 1 WHERE comment_id = #{commentId}")
    void addLikesNum(int commentId);

    @Update("UPDATE comment SET like_num = like_num - 1 WHERE comment_id = #{commentId}")
    void decreaseLikeNum(int commentId);

    @Delete("DELETE from comment where comment_id=#{commentId} or parent_id=#{commentId} or root_parent=#{commentId}")
    int deleteCommentById(int commentId);

    @Select("select * from comment where root_parent=#{commentId}")
    List<Comment> selectByRoot(Integer commentId);

    @Select("select COUNT(*) from comment where post_id=#{postId}")
    int selectCountByPost(int postId);

    @Select("select * from comment where post_id=#{postId}")
    List<Comment> selectByPost(int postId);

    @Select("<script>" +
            "SELECT * FROM comment " +
            "<if test='keys != null and keys.size() > 0'>" +
            "WHERE comment_id IN " +
            "<foreach item='item' collection='keys' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</if>" +
            "</script>")
    List<Comment> selectByRootList(@Param("keys") List<Integer> keys);

}
