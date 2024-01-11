package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title planetJoin
 * @description
 * @create 2024/1/11 21:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetJoin {
    @TableId(type = IdType.AUTO)
    private Integer joinId;
    private Integer senderId;
    private Integer accepterId;
    private Integer type;
    private Integer planetId;
    private String reason;
    private Integer status;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String planetName;
}
