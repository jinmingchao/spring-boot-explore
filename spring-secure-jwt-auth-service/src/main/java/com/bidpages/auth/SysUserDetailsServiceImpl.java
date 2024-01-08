package com.bidpages.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bidpages.mapper.SysUserMapper;
import com.bidpages.mdm.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 代替 SpringSecurity 中的 InMemoryUserDetailsManager
 */
@Service
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用username查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);

        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("sysUser不存在!");
        }
        // 查询用户权限信息
        // 封装成UserDetails对象并返回
        return new LoginSysUser(sysUser);
    }
}
