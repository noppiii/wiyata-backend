package com.wiyata.backend.payload.response;

import com.wiyata.backend.model.User;
import com.wiyata.backend.security.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long userId;
    private final String tokenType;
    private final String accessToken;
    private final String refreshToken;
    private final String profileImgUrl;

    @Builder
    private LoginResponse(String accessToken, String refreshToken, Long userId, String profileImgUrl){
        this.userId = userId;
        this.tokenType = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.profileImgUrl = profileImgUrl;
    }

    public static LoginResponse of(JwtToken jwtToken, User user) {
        return LoginResponse.builder()
                .userId(user.getId())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .profileImgUrl(user.getProfileImg())
                .build();
    }
}
