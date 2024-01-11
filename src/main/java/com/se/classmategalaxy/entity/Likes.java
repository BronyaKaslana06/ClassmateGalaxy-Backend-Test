package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author wyx20
 * @version 1.0
 * @title Likes
 * @description
 * @create 2024/1/10 21:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    @TableId(type= IdType.AUTO)
    private Integer likeId;
    private Integer contentType;
    private Integer contentId;
    private Integer userId;
    private Timestamp time;
}
