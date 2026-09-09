package com.buscapecas.app.http.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.buscapecas.app.services.AuthService;
import com.buscapecas.app.services.RateLimitService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitService rateLimitService;

    public RateLimitInterceptor(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        HttpSession session = request.getSession(true);

        Object usuarioId =
                session.getAttribute(AuthService.SESSION_USUARIO_ID);

        if (usuarioId instanceof Long) {

            rateLimitService.verificarPorUsuarioId(
                    (Long) usuarioId
            );

        } else {

            rateLimitService.verificarAnonimo(session);
        }

        return true;
    }
}