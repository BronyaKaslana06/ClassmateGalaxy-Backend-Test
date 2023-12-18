package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.service.UserRegisterValidatorService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户注册验证服务实现类
 */
@Service
public class UserRegisterValidatorServiceImpl implements UserRegisterValidatorService {

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_!@#$%^&*()-+=<>?{}|~.]{4,50}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isUsernameValid(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    @Override
    public boolean isPasswordValid(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }
}
