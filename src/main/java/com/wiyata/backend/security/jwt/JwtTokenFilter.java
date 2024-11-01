package com.wiyata.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiyata.backend.constant.ErrorCode;
import com.wiyata.backend.exception.CustomException;
import com.wiyata.backend.payload.response.ErrorResponse;
import com.wiyata.backend.util.JwtTokenUtils;
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

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            String accessToken = JwtTokenUtils.extractBearerToken(request.getHeader("accessToken"));


            if (!request.getRequestURI().equals("/api/user/rotate") && accessToken != null) {

                Authentication authentication = jwtTokenProvider.getAuthenticationByAccessToken(accessToken);
                jwtTokenProvider.checkLogin(authentication.getName());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

            filterChain.doFilter(request, response);
        }catch (CustomException e){
            ErrorCode errorCode = e.getErrorCode();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(errorCode.getStatus().value());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, accessToken, refreshToken");
            if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                log.error(errorCode.getMessage());
                response.getWriter().write(
                        objectMapper.writeValueAsString(new ErrorResponse(errorCode.getMessage()))
                );
            }

        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/api/users/register", "/api/users/login",
                "/api/users/send-email", "/api/users/auth-email"
        };

        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
