package com.buscapecas.app.http;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.buscapecas.app.http.interceptors.RateLimitInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;

    public WebConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/veiculos/**")
                .excludePathPatterns("/mock/veiculos/**")
                .excludePathPatterns("/usuarios/**");
    }
}