package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title FollowDto
 * @description
 * @create 2024/1/2 22:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {
    private Integer follower_id;
    private Integer followed_id;
}
