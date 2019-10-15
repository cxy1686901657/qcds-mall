package com.qc.mall.config;

import com.qc.mall.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author qc
 * @date 2019/9/22
 * @description
 * @project mall
 */
@Configuration
public class WebMvcConfiguration  extends WebMvcConfigurerAdapter {
    @Autowired
    AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/","/login","/error","/**/*.json",
                        "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg","/**/*.map",
                        "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg");
        super.addInterceptors(registry);
    }
}
