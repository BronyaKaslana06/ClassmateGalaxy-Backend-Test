package com.se.classmategalaxy.util;



import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.qcloud.cos.utils.DateUtils;
import com.se.classmategalaxy.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    /**
     * 创建map用于存储所有的令牌
     *
     * token  -  User
     */
    private static Map<String, User> tokenMap=new HashMap<>();

    /**
     * 生成token，存储token-user对应关系
     * 返回token令牌
     * @param user
     * @return
     */
    public static String generateToken(User user){
        //生成唯一不重复的字符串
//        String token = UUID.randomUUID().toString();
//        tokenMap.put(token,user);
//        return token;
        return JWT.create().withAudience(user.getUserId().toString()).withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(user.getPassword())); //2小时后过期

    }

    /**
     * 验证token是否合法
     * @param token
     * @return
     */
    public static boolean verify(String token){
        return tokenMap.containsKey(token);
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public static User getUser(String token){
        return tokenMap.get(token);
    }
}