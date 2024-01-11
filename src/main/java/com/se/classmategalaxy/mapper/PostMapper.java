package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.PostUpdateDto;
import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title PostMapper
 * @description
 * @create 2024/1/10 15:20
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Select("select * FROM post where planet_id=#{planetId} limit #{start},#{pageSize} ")
    List<Post> selectPageByPlanet(int planetId, int start, int pageSize, int userId);

    @Select("select COUNT(*) from post where planet_id=#{planetId}")
    int selectPlanetCount(int planetId);

    @Insert("INSERT INTO post (title, content, photo_url, visibility, author_id, planet_id) VALUES (#{title}, #{content}, #{photoUrl}, #{visibility}, #{userId}, #{planetId})")
    Integer releasePost(ReleasePostDto releasePostDto);

    @Update("UPDATE post SET like_num = like_num + 1 WHERE post_id = #{postId}")
    void addLikesNum(int postId);

    @Update("UPDATE post SET collect_num = collect_num + 1 WHERE post_id = #{postId}")
    void addCollectsNum(int postId);

    @Update("UPDATE post SET view_num = view_num + 1 WHERE post_id = #{postId}")
    void addViewsNum(int postId);

    @Update("UPDATE post SET like_num = like_num - 1 WHERE post_id = #{postId}")
    void decreaseLikeNum(int postId);

    @Update("UPDATE post SET collect_num = collect_num - 1 WHERE post_id = #{postId} ")
    void decreaseCollectNum(int postId);

    @Select("select COUNT(*) from post where post_id=#{postId} and author_id=#{userId} limit 1")
    int checkCanDelete(int postId, int userId);

    @Select("select COUNT(*) from post where author_id=#{userId}")
    int selectPersonalCount(int userId);

    @Select("select * FROM post where author_id=#{userId} limit #{start},#{pageSize} ")
    List<Post> selectPageByPersonal(int start, int pageSize, int userId);

    @Select("SELECT * " +
            "FROM post " +
            "ORDER BY view_num DESC " +
            "LIMIT #{num}; ")
    List<Post> getTopPost(int num);

    @Select("select * FROM post where planet_id=#{planetId} ORDER BY view_num DESC limit 2 ")
    List<Post> selectPlanetTop(int planetId);

    @Update("UPDATE post set title=#{title},content=#{content},photo_url=#{photoUrl},alter_time=CURRENT_TIMESTAMP where post_id=#{postId}")
    int updatePost(PostUpdateDto postUpdateDto);
}
