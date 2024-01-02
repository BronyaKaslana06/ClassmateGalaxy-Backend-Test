package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title UserUpdateDto
 * @description
 * @create 2024/1/2 19:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private int userId;
    private String nickName;
    private String phoneNumber;
    private String email;
    private String personalTag;
}
