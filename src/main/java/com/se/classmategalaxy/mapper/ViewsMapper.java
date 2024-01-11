package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Views;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wyx20
 * @version 1.0
 * @title ViewsMapper
 * @description
 * @create 2024/1/10 22:08
 */
@Mapper
public interface ViewsMapper extends BaseMapper<Views> {

    @Insert("INSERT INTO views (user_id,content_id,type) values (#{userId},#{postId},1)")
    void addPostViews(int userId, int postId);
}
