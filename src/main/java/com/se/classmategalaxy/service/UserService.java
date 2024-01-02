package com.se.classmategalaxy.service;

import com.se.classmategalaxy.dto.FollowDto;
import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.dto.UserDetailDto;
import com.se.classmategalaxy.dto.UserUpdateDto;
import com.se.classmategalaxy.entity.User;

import java.util.HashMap;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    int getTotalUserCount();
    User getByAccount(String account);
    void registerUser(RegisterInfo registerInfo);
    boolean authenticateUser(String inputPassword, String hashedPasswordFromDatabase);
    Boolean updateLoginTime(int userId);
    HashMap<String,Object> selectById(int userId);
    List<User> selectUserPage(String keyword,int pageNum,int pageSize);

    HashMap<String, Object> updateUserInfo(UserUpdateDto userUpdateDto);

    HashMap<String, Object> getFollow(Integer userId);

    HashMap<String, Object> cancelFollow(FollowDto followDto);

    HashMap<String, Object> addFollow(FollowDto followDto);
}
