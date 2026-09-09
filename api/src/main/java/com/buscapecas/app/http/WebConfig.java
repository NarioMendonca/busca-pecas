package com.buscapecas.app.http;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.buscapecas.app.http.interceptors.AuthInterceptor;
import com.buscapecas.app.http.interceptors.RateLimitInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    public WebConfig(
            AuthInterceptor authInterceptor,
            RateLimitInterceptor rateLimitInterceptor) {

        this.authInterceptor = authInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(authInterceptor)
            .addPathPatterns(
                    "/perfil/**",
                    "/admin/**"
            );

    registry.addInterceptor(rateLimitInterceptor)
            .addPathPatterns("/veiculos/**")
            .excludePathPatterns("/mock/veiculos/**");
}
}