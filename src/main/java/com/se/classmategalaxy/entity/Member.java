package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title Member
 * @description
 * @create 2024/1/11 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @TableId
    private Integer membershipId;
    private Integer planetId;
    private Timestamp joinTime;
    private Integer userId;
    private String userName;
    private String planetName;
    @TableField(exist = false)
    private String userPhoto;
    @TableField(exist = false)
    private List<String> tagList;

}
