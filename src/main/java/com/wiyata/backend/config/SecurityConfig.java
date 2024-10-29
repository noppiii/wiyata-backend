package com.wiyata.backend.config;

import com.wiyata.backend.security.jwt.JwtTokenProvider;
import com.wiyata.backend.security.oauth2.OAuthSuccessHandler;
import com.wiyata.backend.service.impl.CustomOAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private JwtTokenProvider jwtTokenProvider;
    private CustomOAuth2UserServiceImpl customOAuth2UserService;
    private OAuthSuccessHandler OAuthSuccessHandler;
}
