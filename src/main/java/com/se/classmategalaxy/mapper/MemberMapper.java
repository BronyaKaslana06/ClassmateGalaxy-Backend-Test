package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.HandleApplyDto;
import com.se.classmategalaxy.entity.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title MemberMapper
 * @description
 * @create 2024/1/11 11:25
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    @Select("select * from member where user_id=#{userId}")
    List<Member> getMembershipsByUserId(Integer userId);

    @Select("select * from member")
    List<Member> getAllMemberships();

    @Select("select planet_id from member where user_id=#{userId}")
    List<Integer> getPlanetsByUserId(Integer userId);

    @Select("select COUNT(*) from member where user_id=#{userId} and planet_id=#{planetId} limit 1")
    Integer checkIfJoined(Integer planetId, Integer userId);

    @Select("SELECT * from member where planet_id=#{planetId} limit 5")
    List<Member> selectMemberByPlanet(int planetId);

    @Insert("INSERT INTO member (planet_id,user_id,planet_name,user_name) values (#{planetId},#{userId},#{planetName},#{userName})")
    int addMember(HandleApplyDto handleApplyDto);

    @Delete("DELETE from member where planet_id=#{planetId} and user_id=#{userId}")
    int deleteMember(int planetId, int userId);
}
