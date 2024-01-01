package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.dto.RegisterInfo;
import com.se.classmategalaxy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select count(*) from user")
    public int getTotalUserCount();

    @Select("select * from user where account = #{account}")
    public User getByAccount(String account);

    @Select("INSERT INTO user (account, password,nickname,phone,email,personal_tag)" +
            "VALUES (#{registerInfo.account}, #{hashedPassword},'小星',#{registerInfo.phone},#{registerInfo.email},#{registerInfo.personalTag})")
    public void saveRegisterInfo(RegisterInfo registerInfo, String hashedPassword);

    @Update("UPDATE user SET last_login = CURRENT_TIMESTAMP WHERE user_id = #{userId}")
    public int updateLoginTime(int userId);

    @Select("SELECT * from user where nickname LIKE CONCAT('%', #{keyword}, '%') limit #{start},#{pageSize} ")
    public List<User> selectUserPage(String keyword, int start, int pageSize);
}
