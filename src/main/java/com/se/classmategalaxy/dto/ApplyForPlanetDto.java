package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title ApplyForDto
 * @description
 * @create 2024/1/11 21:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyForPlanetDto {
    private Integer planetId;
    private Integer userId;
    private String reason;
    private Integer ownerId;
}
