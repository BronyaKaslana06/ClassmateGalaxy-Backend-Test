package com.se.classmategalaxy.controller;

import com.se.classmategalaxy.dto.ApplyForPlanetDto;
import com.se.classmategalaxy.dto.HandleApplyDto;
import com.se.classmategalaxy.dto.InviteInfoDto;
import com.se.classmategalaxy.dto.ReleasePostDto;
import com.se.classmategalaxy.entity.Planet;
import com.se.classmategalaxy.service.PlanetService;
import com.se.classmategalaxy.service.RecommendService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title PlanetController
 * @description
 * @create 2024/1/11 10:42
 */
@RestController
@RequestMapping("/planet")
public class PlanetController {
    @Autowired
    PlanetService planetService;

    @Autowired
    RecommendService recommendService;

    @PostMapping("/create")
    @ApiOperation(notes = "返回状态信息", value = "发布星球")
    public HashMap<String,Object> createPlanet(@RequestBody Planet planet){
        return planetService.createPlanet(planet);
    }

    @GetMapping("/recommend")
    @ApiOperation(notes = "返回推荐的星球信息", value = "推荐星球")
    public HashMap<String,Object> recommendPlanet(@RequestParam int userId){
        return recommendService.recommendPlanetsForUser(userId);
    }

    @GetMapping("/search")
    @ApiOperation(notes = "返回搜索到的星球信息", value = "根据关键词搜索星球")
    public HashMap<String,Object> searchPlanet(@RequestParam String keyword){
        return planetService.searchPlanet(keyword);
    }

    @GetMapping("/info")
    @ApiOperation(notes = "返回星球基本信息", value = "根据星球Id获取星球基本信息(没加入前)")
    public HashMap<String,Object> getPlanetInfo(@RequestParam int planetId){
        return planetService.getPlanetInfo(planetId);
    }

    @PostMapping("/apply")
    @ApiOperation(notes = "返回状态信息", value = "申请加入星球")
    public HashMap<String,Object> applyForPlanet(@RequestBody ApplyForPlanetDto applyForPlanetDto){
        return planetService.applyForPlanet(applyForPlanetDto);
    }

    @PostMapping("/invite")
    @ApiOperation(notes = "此处userId为想要邀请的人的id,返回状态信息", value = "星主邀请他人加入星球")
    public HashMap<String,Object> inviteForPlanet(@RequestBody InviteInfoDto inviteInfoDto){
        return planetService.inviteForPlanet(inviteInfoDto);
    }

    @GetMapping("/getApplyList")
    @ApiOperation(notes = "返回申请列表", value = "星主查看申请列表")
    public HashMap<String,Object> getApplyList(@RequestParam int userId,@RequestParam int planetId){
        return planetService.getApplyList(userId,planetId);
    }

    @GetMapping("/getInviteList")
    @ApiOperation(notes = "请求参数为userId,返回列表具体信息", value = "用户个人查看邀请自己的列表")
    public HashMap<String,Object> getInviteList(@RequestParam int userId){
        return planetService.getInviteList(userId);
    }

    @PostMapping("/handleApply")
    @ApiOperation(notes = "userId为申请人的id，choice为1则同意，0为拒绝返回状态信息", value = "星主处理某个加入申请")
    public HashMap<String,Object> handlePlanetApply(@RequestBody HandleApplyDto handleApplyDto){
        return planetService.handlePlanetApply(handleApplyDto);
    }

    @DeleteMapping("/deleteMember")
    @ApiOperation(notes = "删除星球内成员", value = "星主删除星球内某个成员")
    public HashMap<String,Object> deleteMember(@RequestParam int planetId,@RequestParam int userId){
        return planetService.deleteMember(planetId,userId);
    }

    @GetMapping("/getJoined")
    @ApiOperation(notes = "返回用户加入的星球信息", value = "获取用户加入的星球")
    public HashMap<String,Object> getJoinedPlanet(@RequestParam int userId){
        return planetService.getJoinedPlanet(userId);
    }

    @GetMapping("/getCreated")
    @ApiOperation(notes = "返回用户创建的星球信息", value = "获取用户创建的星球")
    public HashMap<String,Object> getCreatedPlanet(@RequestParam int userId){
        return planetService.getCreatedPlanet(userId);
    }

}
