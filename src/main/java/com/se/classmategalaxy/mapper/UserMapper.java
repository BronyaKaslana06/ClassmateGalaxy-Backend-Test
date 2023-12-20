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

    @Select("INSERT INTO user (account, password,phone,email,personal_tag)" +
            "VALUES (#{registerInfo.account}, #{hashedPassword},#{registerInfo.phone},#{registerInfo.email},#{registerInfo.personalTag})")
    public void saveRegisterInfo(RegisterInfo registerInfo, String hashedPassword);

    @Update("UPDATE user SET last_login = CURRENT_TIMESTAMP WHERE user_id = #{userId}")
    public int updateLastLoginTime(int userId);

    public List<User> selectUserPage(String keyword,int pageNum,int pageSize);
}
