package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.dto.UserUpdateDto;
import com.se.classmategalaxy.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select count(*) from user")
    int getTotalUserCount();

    @Select("select * from user where account = #{account}")
    User getByAccount(String account);

    @Select("INSERT INTO user (account, password,nickname,phone,email,personal_tag)" +
            "VALUES (#{registerInfo.account}, #{hashedPassword},'小星',#{registerInfo.phone},#{registerInfo.email},#{registerInfo.personalTag})")
    void saveRegisterInfo(RegisterInfo registerInfo, String hashedPassword);

    @Update("UPDATE user SET last_login = CURRENT_TIMESTAMP WHERE user_id = #{userId}")
    int updateLoginTime(int userId);

    @Select("SELECT * from user where nickname LIKE CONCAT('%', #{keyword}, '%') limit #{start},#{pageSize} ")
    List<User> selectUserPage(String keyword, int start, int pageSize);


    @Update("UPDATE user set nickname=#{nickName},phone=#{phoneNumber},email=#{email},personal_tag=#{personalTag} where user_id=#{userId}")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    int updateUserInfo(UserUpdateDto userUpdateDto);

    @Update("UPDATE user SET last_login = CURRENT_TIMESTAMP WHERE phone = #{phone}")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    int loginByPhone(String phone);

    @Select("SELECT * FROM user WHERE user_id IN (#{userIds})")
    List<User> selectBatchByIds(@Param("userIds") HashSet<Integer> userIds);

    @Select("SELECT * from user")
    List<User> selectAll();
}
