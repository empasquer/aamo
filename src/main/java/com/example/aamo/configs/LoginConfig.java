package com.example.aamo.configs;


import com.example.aamo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Autowired
    LoginService loginService;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the LoginInterceptor and apply it to paths containing "/admin/"
        registry.addInterceptor(loginInterceptor);
    }

}