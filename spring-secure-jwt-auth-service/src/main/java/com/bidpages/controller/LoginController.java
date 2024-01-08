package com.bidpages.controller;

import com.bidpages.mdm.entity.SysUser;
import com.bidpages.mdm.vo.SysUserVO;
import com.bidpages.service.LoginService;
import com.bidpages.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

/**
 *  登陆鉴权接口
 *
 */
@RestController
public class LoginController {

    @Autowired
    public LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUserVO user){
        //登陆
        return loginService.login(user);
    }
}
