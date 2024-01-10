package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title ReleasePostDto
 * @description
 * @create 2024/1/10 17:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleasePostDto {
    String title;
    String content;
    Integer visibility;
    String photoUrl;
    Integer userId;
    Integer planetId;
}
