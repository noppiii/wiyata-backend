package com.wiyata.backend.util;

import com.wiyata.backend.constant.ErrorCode;
import com.wiyata.backend.exception.BadRequestException;
import com.wiyata.backend.service.impl.RedisServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final RedisServiceImpl redisService;
    @Value("${custom.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    public static String extractBearerToken(String token) {
        if (token != null) {
            if (!token.startsWith("Bearer")) throw new BadRequestException(ErrorCode.INVALID_TYPE_TOKEN);
            return token.split(" ")[1].trim();
        }
        return null;
    }

    public boolean isLogin(String email) {
        return redisService.getData("Login_" + email) != null;
    }

    public void setRefreshToken(String username, String refreshToken) {
        redisService.setData("Login_" + username, refreshToken, refreshExpirationTime, TimeUnit.SECONDS);
    }

    public void updateRefreshToken(String username, String refreshToken) {
        setRefreshToken(username, refreshToken);
    }

    public void deleteRefreshToken(String username) {
        redisService.deleteData("Login_" + username);
    }

    public String getRefreshToken(String username) {
        return redisService.getData("Login_" + username).toString();
    }
}
