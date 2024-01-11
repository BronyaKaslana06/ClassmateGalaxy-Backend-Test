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
 * @title Views
 * @description
 * @create 2024/1/10 21:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Views {
    @TableId()
    private Integer viewId;
    private Integer type;
    private Timestamp time;
    private Integer userId;
}
