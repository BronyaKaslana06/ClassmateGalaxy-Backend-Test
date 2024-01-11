package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title PostBriefDto
 * @description
 * @create 2024/1/11 17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBriefDto {
    private Integer postId;
    private String title;
    private Integer userId;
    private String authorName;
    private Integer planetId;
    private String planetName;
    private Integer viewNum;

}
