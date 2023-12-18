package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title UserDetailDto
 * @description
 * @create 2023/12/15 15:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    private Integer userId;
    private String account;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String headPhoto;
    private String token;
    private List<String> personalTag;
    private Timestamp lastLogin;
}
