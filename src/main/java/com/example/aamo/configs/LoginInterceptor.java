package com.example.aamo.configs;

import com.example.aamo.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Check if the requested URL contains "/admin/"
        if (uri.contains("/admin/")) {
            // If user is not logged in, return 401 Unauthorized
            if (!loginService.isUserLoggedIn(request.getSession())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Unauthorized\", \"status\": \"error\"}");
                return false;
            }
        }
        return true; // Allow the request to proceed
    }
}