package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Resource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author wyx20
 * @version 1.0
 * @title ResourceMapper
 * @description
 * @create 2024/1/1 23:08
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Insert("INSERT INTO resource (name, size,resource_key,user_id,planet_id) VALUES (#{name}, #{size},#{resourceKey},#{userId},#{planetId})")
    @Options(useGeneratedKeys = true, keyColumn = "resource_id", keyProperty = "resourceId")
    int uploadFile(Resource resource);
}
