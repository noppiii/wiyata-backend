package com.wiyata.wiyata.backend.payload.response.member;

import com.wiyata.wiyata.backend.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private int status;
    private String message;
    private Object result;

    public AuthResponse successMemberSignUp(String nickname) {
        UserConstant responseCode = UserConstant.SUCCESS_SIGNUP;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);

        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public AuthResponse failMemberSignUp() {
        UserConstant responseCode = UserConstant.DUPLICATE_ID;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successEmailAuth() {
        UserConstant responseCode = UserConstant.SUCCESS_EMAIL_AUTH;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse failEmailAuth() {
        UserConstant responseCode = UserConstant.FAIL_EMAIL_AUTH;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse invalidUsernameOrPassword() {
        UserConstant responseCode = UserConstant.INVALID_USERNAME_OR_PASSWORD;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successLogin(String nickname) {
        UserConstant responseCode = UserConstant.SUCCESS_LOGIN;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public AuthResponse successCreateToken() {
        UserConstant responseCode = UserConstant.SUCCESS_CREATE_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse expireToken() {
        UserConstant responseCode = UserConstant.EXPIRE_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse notFoundRefreshToken() {
        UserConstant responseCode = UserConstant.NOT_FOUND_REFRESH_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse notFoundAccessToken() {
        UserConstant responseCode = UserConstant.NOT_FOUND_ACCESS_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successLogout() {
        UserConstant responseCode = UserConstant.SUCCESS_LOGOUT;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }
}

