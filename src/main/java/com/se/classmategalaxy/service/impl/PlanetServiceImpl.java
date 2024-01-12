package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.dto.ApplyForPlanetDto;
import com.se.classmategalaxy.dto.HandleApplyDto;
import com.se.classmategalaxy.dto.InviteInfoDto;
import com.se.classmategalaxy.entity.Member;
import com.se.classmategalaxy.entity.Planet;
import com.se.classmategalaxy.entity.PlanetJoin;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.*;
import com.se.classmategalaxy.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wyx20
 * @version 1.0
 * @title PlanetServiceImpl
 * @description
 * @create 2024/1/11 10:45
 */
@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetMapper planetMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public HashMap<String, Object> createPlanet(Planet planet) {
        HashMap<String,Object> result=new HashMap<>();
        Integer isCreate=planetMapper.createPlanet(planet);
        if(isCreate>=1){
            result.put("status",1);
            result.put("message","发布成功");
        }
        else{
            result.put("status",0);
            result.put("message","发布失败");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> searchPlanet(String keyword) {
        HashMap<String,Object> result=new HashMap<>();
        List<Planet> planetList=planetMapper.selectLikePlanet(keyword);
        for(Planet planet : planetList){
            planet.setBriefIntroduction(planet.getDescription().substring(0, Math.min(25, planet.getDescription().length())));
        }
        result.put("status",1);
        result.put("totalNum",planetList.size());
        result.put("planetList",planetList);
        return result;
    }

    @Override
    public HashMap<String, Object> getPlanetInfo(int planetId) {
        HashMap<String,Object> result=new HashMap<>();
        Planet planet=planetMapper.selectById(planetId);
        List<String> tagsList = planet.getPlanetTags() != null && !planet.getPlanetTags().isEmpty() ?
                Arrays.asList(planet.getPlanetTags().split(",")) :
                Collections.emptyList();
        planet.setTagList(tagsList);
        result.put("planetInfo",planet);
        User owner=userMapper.selectById(planet.getOwnerId());
        String ownerTag= owner.getPersonalTag();
        owner.setTagList((ownerTag != null && !ownerTag.isEmpty()) ?
                Arrays.asList(ownerTag.split(",")) : new ArrayList<>());
        result.put("owner",owner);
        int postNum=postMapper.selectPlanetCount(planetId);
        result.put("postNum",postNum);
        int resourceNum=resourceMapper.selectPlanetCount(planetId);
        result.put("resourceNum",resourceNum);
        List<Member> memberList=memberMapper.selectMemberByPlanet(planetId);

        for(Member member : memberList){
            User user=userMapper.selectById(member.getUserId());
            member.setUserPhoto(user.getHeadPhoto());
            String personalTag= user.getPersonalTag();
            member.setTagList((personalTag != null && !personalTag.isEmpty()) ?
                    Arrays.asList(personalTag.split(",")) : new ArrayList<>());
        }
        result.put("memberList",memberList);

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取 createTime 对应的 LocalDateTime
        LocalDateTime createDateTime = planet.getCreateTime().toLocalDateTime();
        // 获取 createTime 的日期部分
        LocalDate createDate = createDateTime.toLocalDate();
        // 计算两个日期之间的天数差
        long daysDiff = currentDate.toEpochDay() - createDate.toEpochDay();
        result.put("days",daysDiff+1);
        result.put("top2Posts",postMapper.selectPlanetTop(planetId));
        return result;
    }

    @Override
    public HashMap<String, Object> applyForPlanet(ApplyForPlanetDto applyForPlanetDto) {
        HashMap<String,Object> result=new HashMap<>();
        if(planetMapper.checkPlanetExist(applyForPlanetDto.getPlanetId(),applyForPlanetDto.getOwnerId())>0) {
            if (planetMapper.applyForPlanet(applyForPlanetDto) > 0) {
                result.put("status", 1);
                result.put("message", "提交申请成功");
            } else {
                result.put("status", 0);
                result.put("message", "信息有误，提交申请失败");
            }
        }
        else{
            result.put("status", 0);
            result.put("message", "信息有误，提交申请失败");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getApplyList(int userId, int planetId) {
        HashMap<String,Object> result=new HashMap<>();
        List<PlanetJoin> planetJoins=planetMapper.getApplyList(userId,planetId);

        List<User> userList=userMapper.selectAll();
        HashMap<Integer,User> userMap=new HashMap<>();
        for(User user : userList){
            userMap.put(user.getUserId(),user);
        }
        List<Planet> planetList=planetMapper.selectAll();
        HashMap<Integer,Planet> planetMap=new HashMap<>();
        for(Planet planet : planetList){
            planetMap.put(planet.getPlanetId(),planet);
        }

        for (PlanetJoin planetJoin : planetJoins){
            planetJoin.setUserName(userMap.get(planetJoin.getSenderId()).getNickname());
            planetJoin.setPlanetName(planetMap.get(planetJoin.getPlanetId()).getPlanetName());
        }
        result.put("applyList",planetJoins);
        result.put("status",1);
        result.put("message","获取申请列表成功");
        return result;
    }

    @Override
    public HashMap<String, Object> handlePlanetApply(HandleApplyDto handleApplyDto) {
        HashMap<String,Object> result=new HashMap<>();
        if(handleApplyDto.getChoice()==1){
            planetMapper.updateStatus(handleApplyDto.getJoinId());
            memberMapper.addMember(handleApplyDto);
            planetMapper.addMemberNum(handleApplyDto.getPlanetId());
            result.put("status",1);
            result.put("message","已同意该用户加入星球");
        }
        else if(handleApplyDto.getChoice()==0){
            planetMapper.updateStatus(handleApplyDto.getJoinId());
            result.put("status",1);
            result.put("message","拒绝该用户加入星球");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> deleteMember(int planetId, int userId) {
        HashMap<String,Object> result=new HashMap<>();
        if(memberMapper.deleteMember(planetId,userId)>0){
            planetMapper.decreaseMemberNum(planetId);
            result.put("status",1);
            result.put("message","成员删除成功");
        }
        else{
            result.put("status",0);
            result.put("message","删除成员失败");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getJoinedPlanet(int userId) {
        HashMap<String,Object> result=new HashMap<>();
        List<Integer> planetIds=memberMapper.getPlanetsByUserId(userId);
        List<Planet> planetList=new ArrayList<>();
        for(Integer planetId : planetIds){
            Planet planet=planetMapper.selectById(planetId);
            if(planet!=null) {
                planet.setBriefIntroduction(planet.getDescription().substring(0, Math.min(25, planet.getDescription().length())));
                planetList.add(planet);
            }
        }
        result.put("planetList",planetList);
        result.put("status",1);
        result.put("message","获取已加入星球列表成功");
        return result;
    }

    @Override
    public HashMap<String, Object> getCreatedPlanet(int userId) {
        HashMap<String,Object> result=new HashMap<>();
        List<Planet> planetList=planetMapper.getByUserId(userId);
        for(Planet planet : planetList){
            planet.setBriefIntroduction(planet.getDescription().substring(0, Math.min(25, planet.getDescription().length())));
        }
        result.put("planetList",planetList);
        result.put("status",1);
        result.put("message","获取创建的星球列表成功");
        return result;
    }

    @Override
    public HashMap<String, Object> inviteForPlanet(InviteInfoDto inviteInfoDto) {
        HashMap<String,Object> result=new HashMap<>();
        if(planetMapper.checkPlanetExist(inviteInfoDto.getPlanetId(),inviteInfoDto.getOwnerId())>0) {
            if (planetMapper.inviteForPlanet(inviteInfoDto) > 0) {
                result.put("status", 1);
                result.put("message", "发送邀请成功");
            } else {
                result.put("status", 0);
                result.put("message", "信息有误，发送邀请失败");
            }
        }
        else{
            result.put("status", 0);
            result.put("message", "信息有误，发送邀请失败");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getInviteList(int userId) {
        HashMap<String,Object> result=new HashMap<>();
        List<PlanetJoin> planetJoins=planetMapper.getInviteList(userId);
        List<User> userList=userMapper.selectAll();
        HashMap<Integer,User> userMap=new HashMap<>();
        for(User user : userList){
            userMap.put(user.getUserId(),user);
        }
        List<Planet> planetList=planetMapper.selectAll();
        HashMap<Integer,Planet> planetMap=new HashMap<>();
        for(Planet planet : planetList){
            planetMap.put(planet.getPlanetId(),planet);
        }

        for (PlanetJoin planetJoin : planetJoins){
            planetJoin.setUserName(userMap.get(planetJoin.getSenderId()).getNickname());
            planetJoin.setPlanetName(planetMap.get(planetJoin.getPlanetId()).getPlanetName());
        }
        result.put("applyList",planetJoins);
        result.put("status",1);
        result.put("message","获取邀请列表成功");
        return result;
    }
}
