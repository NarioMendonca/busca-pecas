package com.buscapecas.app.http.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.buscapecas.app.exceptions.NaoAutenticadoException;
import com.buscapecas.app.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute(AuthService.SESSION_USUARIO_ID) == null) {

            throw new NaoAutenticadoException();
        }

        return true;
    }
}