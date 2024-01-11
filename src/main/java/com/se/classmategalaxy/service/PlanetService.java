package com.se.classmategalaxy.service;

import com.se.classmategalaxy.dto.ApplyForPlanetDto;
import com.se.classmategalaxy.dto.HandleApplyDto;
import com.se.classmategalaxy.dto.InviteInfoDto;
import com.se.classmategalaxy.entity.Planet;

import java.util.HashMap;

/**
 * @author wyx20
 * @version 1.0
 * @title PlanetService
 * @description
 * @create 2024/1/11 10:45
 */
public interface PlanetService {
    HashMap<String, Object> createPlanet(Planet planet);

    HashMap<String, Object> searchPlanet(String keyword);

    HashMap<String, Object> getPlanetInfo(int planetId);

    HashMap<String, Object> applyForPlanet(ApplyForPlanetDto applyForPlanetDto);

    HashMap<String, Object> getApplyList(int userId, int planetId);

    HashMap<String, Object> handlePlanetApply(HandleApplyDto handleApplyDto);

    HashMap<String, Object> deleteMember(int planetId, int userId);

    HashMap<String, Object> getJoinedPlanet(int userId);

    HashMap<String, Object> getCreatedPlanet(int userId);

    HashMap<String, Object> inviteForPlanet(InviteInfoDto inviteInfoDto);

    HashMap<String, Object> getInviteList(int userId);
}
