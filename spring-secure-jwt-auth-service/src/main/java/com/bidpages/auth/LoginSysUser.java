package com.bidpages.auth;

import com.bidpages.mdm.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 实现 SpringSecurity 中的 UserDetails接口
 * 在 SysUserDetailsServiceImpl中返回
 */
@Data
public class LoginSysUser implements UserDetails {

    private SysUser sysUser;

    public LoginSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 以下为让SpringSecurity进行异常处理的方法
     * 暂时都返回 true 即没有异常
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
