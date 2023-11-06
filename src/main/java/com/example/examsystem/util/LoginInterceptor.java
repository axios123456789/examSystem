package com.example.examsystem.util;

import cn.hutool.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//登录拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;

    //请求拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先拿到token
        String token = request.getHeader("token");
        System.out.println("token=="+token);

        if (token == null || token.isEmpty()) {
            //token为空
            //设置响应码 401 没有权限
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }

        //有token
        //校验 从redis中通过token获取值
        Object o = redisTemplate.opsForValue().get(token);
        if (o == null){
            //token失效
            //设置响应码 401 没有权限
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }else {
          return true;
        }
    }
}
