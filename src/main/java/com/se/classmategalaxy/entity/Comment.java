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
 * @title Comment
 * @description
 * @create 2023/12/25 20:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId(type= IdType.AUTO)
    private Integer commentId;
    private String content;
    private Timestamp time;
    private Integer likeNum;
    private Integer parentId;
    private Integer rootParent;
    private Integer postId;
    private Integer userId;
}
