package com.mybatis.myboard;

import com.mybatis.myboard.interceptor.LoginCheckInterceptor;
import com.mybatis.myboard.interceptor.MsgRemoveInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    public InterceptorConfig(LoginCheckInterceptor loginCheckInterceptor, MsgRemoveInterceptor msgRemoveInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
        this.msgRemoveInterceptor = msgRemoveInterceptor;
    }

    LoginCheckInterceptor loginCheckInterceptor;
    MsgRemoveInterceptor msgRemoveInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginCheckInterceptor).order(1)
                .addPathPatterns("/user/*.do")
                .excludePathPatterns("/user/findpw.do")
                .excludePathPatterns("/user/find.do")
                .excludePathPatterns("/user/login.do")
                .excludePathPatterns("/user/signup.do")
                .addPathPatterns("/board/*.do")
                .addPathPatterns("/board/*/*.do")
                .excludePathPatterns("/board/list.do")
                .excludePathPatterns("/board/*/detail.do")
                .excludePathPatterns("/board/prefer/*.do");
        registry.addInterceptor(msgRemoveInterceptor).order(2)
                .addPathPatterns("/")
                .addPathPatterns("/**/*.do");
    }
}
