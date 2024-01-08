package com.bidpages.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//TODO 找找这个配置的官方文档
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO 关闭csrf, 是干什么用的
        http.csrf((httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()));

        //TODO 关闭csrf, 是干什么用的
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//        http.csrf().disable() //TODO 关闭csrf, 是干什么用的
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //TODO 不通过Session获取SecurityContext 是干什么用的

        //放行url

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.POST,"/user/login").permitAll() // 对于登陆接口, 放行
                .anyRequest().authenticated() //其他请求全部需要鉴权验证
        );

        return http.build();

    }

    /**
     * 登录时需要调用AuthenticationManager.authenticate执行一次校验
     *
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
