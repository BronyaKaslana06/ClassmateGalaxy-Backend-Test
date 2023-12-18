package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * @author wyx20
 * @version 1.0
 * @title UserFollows
 * @description
 * @create 2023/12/11 14:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollows {
    @TableId
    private Integer follow_id;
    private Integer follower_id;
    private Integer followed_id;
    private Timestamp follow_time;
    private Integer status;

}
