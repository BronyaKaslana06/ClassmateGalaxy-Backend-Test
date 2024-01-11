package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.ApplyForPlanetDto;
import com.se.classmategalaxy.dto.InviteInfoDto;
import com.se.classmategalaxy.entity.Planet;
import com.se.classmategalaxy.entity.PlanetJoin;
import com.se.classmategalaxy.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title PlanetMapper
 * @description
 * @create 2024/1/11 10:51
 */
@Mapper
public interface PlanetMapper extends BaseMapper<Planet> {

    @Insert("INSERT INTO planet (planet_name,description,picture,owner_id,planet_tags) values (#{planetName},#{description},#{picture},#{ownerId},#{planetTags})")
    Integer createPlanet(Planet planet);

    @Select("SELECT * from planet where planet_name LIKE CONCAT('%', #{tag}, '%') or planet_tags LIKE CONCAT('%', #{tag}, '%') or description LIKE CONCAT('%', #{tag}, '%')")
    List<Planet> selectLikePlanet(String tag);

    @Insert("INSERT INTO planet_join (planet_id,sender_id,accepter_id,reason,type,status) values (#{planetId},#{userId},#{ownerId},#{reason},2,0) ")
    int applyForPlanet(ApplyForPlanetDto applyForPlanetDto);

    @Select("SELECT count(*) from planet where planet_id=#{planetId} and owner_id=#{ownerId} limit 1")
    int checkPlanetExist(int planetId,int ownerId);

    @Select("SELECT * from planet_join where accepter_id=#{userId} and planet_id=#{planetId} and type=2 and status=0")
    List<PlanetJoin> getApplyList(int userId, int planetId);

    @Select("SELECT * from planet")
    List<Planet> selectAll();

    @Update("UPDATE planet_join set status=1 where join_id=#{joinId}")
    void updateStatus(int joinId);

    @Update("UPDATE planet SET member_num = member_num + 1 WHERE planet_id = #{planetId}")
    void addMemberNum(Integer planetId);

    @Select("SELECT count(*) from planet where planet_id=#{planetId} and owner_id=#{userId}")
    int checkIfOwn(Integer planetId, Integer userId);

    @Update("UPDATE planet SET member_num = member_num - 1 WHERE planet_id = #{planetId}")
    void decreaseMemberNum(int planetId);

    @Select("SELECT * from planet where owner_id=#{userId}")
    List<Planet> getByUserId(int userId);

    @Insert("INSERT INTO planet_join (planet_id,sender_id,accepter_id,reason,type,status) values (#{planetId},#{ownerId},#{userId},#{reason},1,0) ")
    int inviteForPlanet(InviteInfoDto inviteInfoDto);

    @Select("SELECT * from planet_join where accepter_id=#{userId} and type=1 and status=0")
    List<PlanetJoin> getInviteList(int userId);
}
