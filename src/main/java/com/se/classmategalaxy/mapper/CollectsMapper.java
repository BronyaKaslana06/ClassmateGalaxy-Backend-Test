package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Collects;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CollectsMapper
 * @description
 * @create 2024/1/10 22:11
 */
@Mapper
public interface CollectsMapper extends BaseMapper<Collects> {

    @Select("SELECT count(*) from collects where user_id=#{userId} and content_id=#{postId} limit 1")
    int checkIfCollected(int userId, int postId);

    @Insert("INSERT INTO collects (user_id,content_id,type) values (#{userId},#{postId},1)")
    void addPostCollects(int userId, int postId);

    @Delete("DELETE from collects where user_id=#{userId} and content_id=#{postId} ")
    void cancelCollect(int userId, int postId);

    @Select("select * from collects where user_id=#{userId} and type=1 limit #{start},#{pageSize}")
    List<Collects> selectByUserId(int userId,int start,int pageSize);

    @Select("select count(*) from collects where user_id=#{userId} and type=1 ")
    Integer selectUserCount(int userId);
}
