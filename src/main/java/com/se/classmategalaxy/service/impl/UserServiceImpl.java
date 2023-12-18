package com.se.classmategalaxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.dto.UserDetailDto;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public Boolean updateLastLoginTime(int userId) {
        int rowsAffected = userMapper.updateLastLoginTime(userId);
        return rowsAffected > 0;
    }

    @Override
    public UserDetailDto selectById(int userId){
        User user=userMapper.selectById(userId);
        UserDetailDto userDetailDto=new UserDetailDto();
        userDetailDto.setPersonalTag(Arrays.stream(user.getPersonalTag().split(","))
                .map(String::trim) //去除每个标签的空格
                .collect(Collectors.toList()));
        userDetailDto.setUserId(userId);
        userDetailDto.setAccount(user.getAccount());
        userDetailDto.setPassword(user.getPassword());
        userDetailDto.setNickname(user.getNickname());
        userDetailDto.setPhone(user.getPhone());
        userDetailDto.setEmail(user.getEmail());
        userDetailDto.setHeadPhoto(user.getHeadPhoto());
        userDetailDto.setToken(user.getToken());
        userDetailDto.setLastLogin(user.getLastLogin());
        return userDetailDto;
    }

    @Override
    public List<User> selectUserPage(String keyword,int pageNum,int pageSize){
        return userMapper.selectUserPage(keyword, pageNum, pageSize);
    }
}
