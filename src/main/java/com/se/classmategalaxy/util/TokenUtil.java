package com.se.classmategalaxy.util;



import com.se.classmategalaxy.entity.User;

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
        String token = UUID.randomUUID().toString();
        tokenMap.put(token,user);
        return token;
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