package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author wyx20
 * @version 1.0
 * @title CommentInfoDto
 * @description
 * @create 2024/1/11 0:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfoDto {
    private Integer commentId;
    private Integer posterId;
    private String posterName;
    private String posterAvatar;
    private Integer likeNum;
    private Timestamp time;
    private Boolean isLiked;
    private String review;
    private Boolean deletePermission;
}
