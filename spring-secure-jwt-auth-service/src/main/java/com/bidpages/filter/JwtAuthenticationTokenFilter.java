package com.bidpages.filter;

import com.alibaba.fastjson.JSONObject;
import com.bidpages.auth.LoginSysUser;
import com.bidpages.mdm.entity.SysUser;
import com.bidpages.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * extends OncePerRequestFilter
 * 保证请求只被调用一次
 * 原生servlet的Filter可能会被调用多次
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 获取token
        //请求头中获取token
        String jwtToken = request.getHeader("Bearer");
        //解析token
        if (null == jwtToken || 0 == jwtToken.trim().length()) {
            filterChain.doFilter(request, response); //如果没有token, 则放行交给后面的过滤器处理
            return;
        }
        String userId = null;
        try {
            userId = JwtUtil.parseJWT(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("jwttoken非法");
        }
        if (userId == null) {
            throw new RuntimeException("userId为空");
        }
        //2. 从redis中获取用户信息
        String redisKey = "login:" + userId;
        Object loginUser_json = redisTemplate.opsForValue().get(redisKey);
        if (null == loginUser_json) {
            throw new RuntimeException("用户未登录或不存在");
        }

        LoginSysUser loginSysUser = JSONObject.toJavaObject(JSONObject.parseObject((String) loginUser_json), LoginSysUser.class);
        //TODO 3. 获取权限信息
        //4. 存入Security上下文中
        //UsernamePasswordAuthenticationToken构造器用三个参数的，带权限的, 必须用, 因为里面有this.setAuthenticated(true),通知后面的过滤器该用户已经登录
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginSysUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
