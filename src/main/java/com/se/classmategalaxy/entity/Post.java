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
 * @title Post
 * @description
 * @create 2023/12/25 20:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @TableId(type= IdType.AUTO)
    private Integer postId;
    private String title;
    private String content;
    private Integer likeNum;
    private Integer collectNum;
    private Integer viewNum;
    private String photoUrl;
    private Integer visibility;
    private Timestamp publishTime;
    private Timestamp alterTime;
    private Integer planetId;
    private Integer authorId;



}
