package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String account;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String headPhoto;
    private String token;
    private String personalTag;
    private Timestamp lastLogin;

    @TableField(exist = false)
    private Integer followNumber;

    @TableField(exist = false)
    private Integer fansNumber;

    @TableField(exist = false)
    private List<String> tagList;
}
