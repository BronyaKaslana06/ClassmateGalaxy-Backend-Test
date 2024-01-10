package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.dto.FollowDto;
import com.se.classmategalaxy.dto.LoginRequest;
import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.dto.UserUpdateDto;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.entity.UserFollows;
import com.se.classmategalaxy.mapper.FollowMapper;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FollowMapper followMapper;

    /**
     * @description 获取全部用户数量
     * @author wyx20
     * @throws
     * @return int
     * @time 2023/12/11 14:56
     */
    @Override
    public int getTotalUserCount() {
        return userMapper.getTotalUserCount();
    }

    /**
     * @description 根据账号名获取用户信息
     * @author wyx20
     * @param[1] account
     * @throws
     * @return User
     * @time 2023/12/11 14:56
     */
    @Override
    public User getByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    public void registerUser(RegisterInfo registerInfo) {
        // 对密码进行哈希编码
        String hashedPassword = passwordEncoder.encode(registerInfo.getPassword());

        // 将用户注册信息和哈希密码保存到数据库中
        userMapper.saveRegisterInfo(registerInfo, hashedPassword);
    }

    @Override
    public boolean authenticateUser(String inputPassword, String hashedPasswordFromDatabase) {
        // 验证用户输入的密码是否与数据库中的哈希密码匹配
        return passwordEncoder.matches(inputPassword, hashedPasswordFromDatabase);
    }

    @Override
    public Boolean updateLoginTime(int userId) {
        int rowsAffected = userMapper.updateLoginTime(userId);
        return rowsAffected > 0;
    }

    @Override
    public HashMap<String,Object> selectById(int userId){
        HashMap<String,Object> result=new HashMap<>();
        User user=userMapper.selectById(userId);
        if(user!=null) {
            user.setTagList(Arrays.stream(user.getPersonalTag().split(","))
                    .map(String::trim) //去除每个标签的空格
                    .collect(Collectors.toList()));
            user.setFollowNumber(followMapper.getFollowCount(userId));
            user.setFansNumber(followMapper.getFansCount(userId));
            result.put("data", user);
            result.put("status",1);
            result.put("message","信息获取成功");
        }
        else{
            result.put("status",0);
            result.put("message","信息获取失败，无对应userId的用户");
        }
        return result;
    }

    @Override
    public HashMap<String,Object> updateUserInfo(UserUpdateDto userUpdateDto){
        HashMap<String,Object> result=new HashMap<>();
        if(userMapper.updateUserInfo(userUpdateDto)>0){
            User user=userMapper.selectById(userUpdateDto.getUserId());
            user.setTagList(Arrays.stream(user.getPersonalTag().split(","))
                    .map(String::trim) //去除每个标签的空格
                    .collect(Collectors.toList()));
            user.setFollowNumber(followMapper.getFollowCount(userUpdateDto.getUserId()));
            user.setFansNumber(followMapper.getFansCount(userUpdateDto.getUserId()));
            result.put("data",user);
            result.put("status",1);
            result.put("message","更新成功");
        }
        else{
            result.put("status",0);
            result.put("message","更新失败，不存在该userId对应的用户");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getFollow(Integer userId) {
        HashMap<String,Object> result=new HashMap<>();
        List<Integer> followList=followMapper.getFollowedId(userId);
        if(!followList.isEmpty()) {
            List<HashMap<String, Object>> followInfoList = new ArrayList<>();
            for (Integer followed_id : followList) {
                User user = userMapper.selectById(followed_id);
                HashMap<String, Object> followInfoDto = new HashMap<>();
                followInfoDto.put("name", user.getNickname());
                followInfoDto.put("head_photo", user.getHeadPhoto());
                followInfoDto.put("followed_id", followed_id);
                followInfoList.add(followInfoDto);
            }
            result.put("data", followInfoList);
            result.put("status", 1);
            result.put("message", "信息获取成功");
        }
        else{
            result.put("status", 0);
            result.put("message", "关注列表为空或用户id不存在");
        }

        return result;
    }

    @Override
    public HashMap<String, Object> cancelFollow(FollowDto followDto) {
        HashMap<String,Object> result=new HashMap<>();
        if(followMapper.cancelFollow(followDto)>0){
            result.put("status",1);
            result.put("message","取消关注成功");
        }
        else{
            result.put("status",0);
            result.put("message","取消关注失败，关注关系不存在");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> addFollow(FollowDto followDto) {
        HashMap<String,Object> result=new HashMap<>();
        UserFollows userFollows=followMapper.checkIsFollow(followDto);
        if (userFollows!=null&&userFollows.getStatus()==0) {
            followMapper.reFollow(followDto);
            result.put("status",1);
            result.put("message","重新关注成功");
        }
        else if(followMapper.addFollow(followDto)>0){
            result.put("status",1);
            result.put("message","新增关注成功");
        }
        else{
            result.put("status",0);
            result.put("message","新增关注失败");
        }
        return result;
    }


    @Override
    public HashMap<String, Object> loginByPhone(LoginRequest loginRequest) {
        HashMap<String,Object> result=new HashMap<>();
        int userId=userMapper.loginByPhone(loginRequest.getPhone());
        if(userId>0) {
            result.put("user", userMapper.selectById(userId));
            result.put("status",1);
            result.put("message","登录成功");
        }
        else {
            result.put("status",0);
            result.put("message","用户不存在");
        }
        return result;
    }

    @Override
    public List<User> selectUserPage(String keyword,int pageNum,int pageSize){
        int start=(pageNum-1)*pageSize;
        return userMapper.selectUserPage(keyword, start, pageSize);
    }
}
