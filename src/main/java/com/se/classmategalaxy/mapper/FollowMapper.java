package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.FollowDto;
import com.se.classmategalaxy.entity.UserFollows;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title FollowMapper
 * @description
 * @create 2024/1/2 20:26
 */
@Mapper
public interface FollowMapper extends BaseMapper<UserFollows> {
    @Select("SELECT COUNT(*) from user_follows where follower_id=#{userId} and status=1")
    int getFollowCount(int userId);

    @Select("SELECT COUNT(*) from user_follows where followed_id=#{userId} and status=1")
    int getFansCount(int userId);

    @Select("select followed_id from user_follows where follower_id=#{userId} and status=1")
    List<Integer> getFollowedId(Integer userId);

    @Update("update user_follows set status=0 where follower_id=#{follower_id} and followed_id=#{followed_id}")
    int cancelFollow(FollowDto followDto);

    @Update("update user_follows set status=1 where follower_id=#{follower_id} and followed_id=#{followed_id}")
    int reFollow(FollowDto followDto);

    @Insert("INSERT INTO user_follows (follower_id, followed_id,status) VALUES (#{follower_id}, #{followed_id},1)")
    int addFollow(FollowDto followDto);

    @Select("select * from user_follows where follower_id=#{follower_id} and followed_id=#{followed_id} limit 1")
    UserFollows checkIsFollow(FollowDto followDto);
}
