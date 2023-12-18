package com.se.classmategalaxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
}
