package com.sinothk.cloud.comm.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class TokenConfig implements WebMvcConfigurer {

    @Resource(name = "authorizationInterceptor")
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // https://blog.csdn.net/com_it/article/details/95083104
        // 为了解决 VueJS 跨域问题
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**"); //对应的接口
            }
        };
    }
}
