package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title Resource
 * @description
 * @create 2024/1/1 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    @TableId
    private Integer resourceId;
    private String name;
    private String resourceKey;
    private String uploadTime;
    private Integer userId;
    private Integer planetId;
    private Float score;
    private Integer downloadCount;
    private Double Size;
    private String introduction;
}
