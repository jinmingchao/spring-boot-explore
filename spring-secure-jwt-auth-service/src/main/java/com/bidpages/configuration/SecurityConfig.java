package com.bidpages.configuration;

import com.bidpages.filter.JwtAuthenticationTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


//TODO 找找这个配置的官方文档
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

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
                .requestMatchers(HttpMethod.POST, "/user/login").permitAll() // 对于登陆接口, 放行
                .anyRequest().authenticated() //其他请求全部需要鉴权验证

        );
        //JWT 过滤器加入到 UsernamePasswordAuthenticationFilter 过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        //spring security 允许跨域
        http.cors();
//        http.cors((corsConfigurer -> corsConfigurer.configure(http)));
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
