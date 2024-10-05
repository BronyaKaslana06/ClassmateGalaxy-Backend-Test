package com.se.classmategalaxy.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.se.classmategalaxy.entity.User;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)) {
            throw new RuntimeException("无token，请重新登录");
        }
        String userId;
        userId = JWT.decode(token).getAudience().get(0);
        User user = userMapper.selectById(userId);
        if(user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }

        return true;
    }
}
