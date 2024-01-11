package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author wyx20
 * @version 1.0
 * @title ReplyInfoDto
 * @description
 * @create 2024/1/11 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyInfoDto {
    private Integer commentId;
    private Integer posterId;
    private Integer repliedId;
    private String repliedName;
    private String posterName;
    private String posterAvatar;
    private String repliedAvatar;
    private Integer likeNum;
    private Timestamp time;
    private Boolean isLiked;
    private String review;
    private Boolean deletePermission;
}
