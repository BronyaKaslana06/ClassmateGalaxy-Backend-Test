package com.se.classmategalaxy.service;

/**
 * 用户注册验证服务接口
 */
public interface UserRegisterValidatorService {
    boolean isUsernameValid(String username);
    boolean isPasswordValid(String password);
}
