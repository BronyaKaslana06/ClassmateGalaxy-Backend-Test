package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title InviteInfoDto
 * @description
 * @create 2024/1/12 0:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteInfoDto {
    private Integer planetId;
    private Integer userId;
    private String reason;
    private Integer ownerId;
}
