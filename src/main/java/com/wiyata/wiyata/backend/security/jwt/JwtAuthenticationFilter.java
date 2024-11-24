package com.wiyata.wiyata.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        
        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            } else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                boolean validRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);

                if (validRefreshToken && isRefreshToken) {
                    String userName = jwtTokenProvider.getUserName(refreshToken);
                    List<String> roles = jwtTokenProvider.getRoles(userName);
                    String newAccessToken = jwtTokenProvider.createAccessToken(userName, roles);
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    this.setAuthentication(newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/api/v1/auth/signup", "/api/v1/auth/login"
        };

        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
