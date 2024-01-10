package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author wyx20
 * @version 1.0
 * @title Collects
 * @description
 * @create 2024/1/4 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collects {
    @TableId
    private Integer collectId;
    private Integer type;
    private Integer contentId;
    private Timestamp time;
    private Integer favoritesId;
    private Integer userId;
}
