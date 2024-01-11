package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Post;
import com.se.classmategalaxy.entity.Resource;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title ResourceMapper
 * @description
 * @create 2024/1/1 23:08
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Insert("INSERT INTO resource (name, size,resource_key,user_id,planet_id,introduction) VALUES (#{name}, #{size},#{resourceKey},#{userId},#{planetId},#{introduction})")
    @Options(useGeneratedKeys = true, keyColumn = "resource_id", keyProperty = "resourceId")
    int uploadFile(Resource resource);

    @Select("select COUNT(*) from resource where planet_id=#{planetId}")
    int selectPlanetCount(int planetId);

    @Select("select * FROM resource where planet_id=#{planetId} limit #{start},#{pageSize}")
    List<Resource> selectPageByPlanet(int planetId, int start, int pageSize, int userId);

    @Update("UPDATE resource SET download_count = download_count + 1 WHERE resource_id = #{resourceId} ")
    void addDownloadCount(int resourceId);
}
