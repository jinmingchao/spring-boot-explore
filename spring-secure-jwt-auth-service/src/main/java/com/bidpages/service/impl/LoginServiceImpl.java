package com.bidpages.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bidpages.auth.LoginSysUser;
import com.bidpages.mdm.entity.SysUser;
import com.bidpages.mdm.vo.SysUserVO;
import com.bidpages.service.LoginService;
import com.bidpages.utils.JwtUtil;
import com.bidpages.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseResult login(SysUserVO user) {
        ResponseResult<String> rs;
        //调用AuthenticationManager.authenticate 进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticateRes = authenticationManager.authenticate(authenticationToken);

        //如果认证失败, 给出错误信息
        if (null == authenticateRes) {
            throw new RuntimeException("登录失败 - 认证未通过");
        }
        //如果认证通过, 使用用户对象生成Jwt, Jwt存入ResponseResult进行返回
        //Authentication对象中包含了从自定义UserDetails实现类中返回的用户信息, 即new LoginSysUser(sysUser);
        LoginSysUser loginSysUser = ((LoginSysUser) authenticateRes.getPrincipal());
        SysUser loginUser = loginSysUser.getSysUser();
        String jwt = JwtUtil.createJWT(loginUser.getId().toString()); //使用userId创建jwt
        //用户信息&权限存入缓存
        //类型使用redis string类型
        //格式为: login:{USERID} loginSysUser
        redisTemplate.opsForValue().set("login:" + loginUser.getId().toString(), JSONObject.toJSONString(loginSysUser));
        return new ResponseResult(200, "登陆成功!", jwt);
    }
}
