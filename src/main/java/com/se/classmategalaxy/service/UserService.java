package com.se.classmategalaxy.service;

import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.dto.UserDetailDto;
import com.se.classmategalaxy.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    int getTotalUserCount();
    User getByAccount(String account);
    void registerUser(RegisterInfo registerInfo);
    boolean authenticateUser(String inputPassword, String hashedPasswordFromDatabase);
    Boolean updateLastLoginTime(int userId);
    UserDetailDto selectById(int userId);
    List<User> selectUserPage(String keyword,int pageNum,int pageSize);

}
