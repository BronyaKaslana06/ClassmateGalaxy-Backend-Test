package com.se.classmategalaxy.dto;

import lombok.Data;

@Data
public class RegisterInfo {
    private String account;
    private String password;
    private String phone;
    private String email;
    private String personalTag;
}
