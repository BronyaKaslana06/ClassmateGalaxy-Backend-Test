package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
