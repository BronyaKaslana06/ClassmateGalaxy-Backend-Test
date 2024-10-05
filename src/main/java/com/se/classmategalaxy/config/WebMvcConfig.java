package com.se.classmategalaxy.config;

import com.se.classmategalaxy.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                // 不拦截的路径
                .excludePathPatterns("/api/user/login","/error","/v2/api-docs", "/configuration/ui",
                        "/swagger-resources", "/configuration/security", "/swagger-ui/index.html","/webjars/**");
    }

    @Bean
    public JwtInterceptor loginInterceptor(){
        return new JwtInterceptor();
    }
}