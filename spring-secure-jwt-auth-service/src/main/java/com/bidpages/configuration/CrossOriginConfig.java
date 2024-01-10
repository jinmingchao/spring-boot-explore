package com.bidpages.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot跨域请求
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //设置允许跨域的路径
                .allowedOrigins("*")         //设置允许跨域请求的域名
                .allowCredentials(true)      //是否允许cookie
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") //设置允许的请求方式
                .allowedHeaders("*")         //设置允许的header属性
                .maxAge(3600);                //跨域请求时间
    }
}
