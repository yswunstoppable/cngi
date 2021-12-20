package com.occamedu.monitor.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created by Sandwich on 2018-08-13
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;      // 登陆校验
    @Resource
    private BeforeLoginInterceptor beforeLoginInterceptor; // 跨域访问


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有请求登陆之前拦截器: 用于配置跨域请求等
        InterceptorRegistration beforeLoginRegistry = registry.addInterceptor(beforeLoginInterceptor);
        beforeLoginRegistry.addPathPatterns("/**");

        // 不需要登陆校验的地址
        String[] excludePaths = {
                // 错误页面
                "/error",
                // 排除资源请求
                "*.css", "*.js", "*.png", "/dist/**",
                // 排除swagger相关路径
                "/swagger-ui.html", "/configuration/*", "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
        };

        // 登陆拦截请求
        InterceptorRegistration authorizeRegistry = registry.addInterceptor(loginInterceptor);
        authorizeRegistry.addPathPatterns("/**");
        authorizeRegistry.excludePathPatterns(excludePaths);


    }
}
