package com.example.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FrontendInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 强制设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uri = request.getRequestURI();

        // API 请求、静态资源、上传文件直接放行
        if (uri.startsWith("/api/") ||
                uri.startsWith("/static/") ||
                uri.startsWith("/uploads/") ||
                uri.contains(".")) {
            return true;
        }

        // 其他所有请求转发到 index.html
        request.getRequestDispatcher("/index.html").forward(request, response);
        return false;
    }
}