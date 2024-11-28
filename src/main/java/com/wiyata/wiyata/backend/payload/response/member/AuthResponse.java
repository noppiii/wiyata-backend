package com.wiyata.wiyata.backend.payload.response.member;

import com.wiyata.wiyata.backend.constant.AuthConstant;
import com.wiyata.wiyata.backend.constant.MemberConstant;
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
        AuthConstant responseCode = AuthConstant.SUCCESS_SIGNUP;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);

        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public AuthResponse failMemberSignUp() {
        AuthConstant responseCode = AuthConstant.DUPLICATE_ID;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successEmailAuth() {
        AuthConstant responseCode = AuthConstant.SUCCESS_EMAIL_AUTH;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse failEmailAuth() {
        AuthConstant responseCode = AuthConstant.FAIL_EMAIL_AUTH;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse invalidUsernameOrPassword() {
        AuthConstant responseCode = AuthConstant.INVALID_USERNAME_OR_PASSWORD;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successLogin(String nickname) {
        AuthConstant responseCode = AuthConstant.SUCCESS_LOGIN;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public AuthResponse successCreateToken() {
        AuthConstant responseCode = AuthConstant.SUCCESS_CREATE_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse expireToken() {
        AuthConstant responseCode = AuthConstant.EXPIRE_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse notFoundRefreshToken() {
        AuthConstant responseCode = AuthConstant.NOT_FOUND_REFRESH_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse notFoundAccessToken() {
        AuthConstant responseCode = AuthConstant.NOT_FOUND_ACCESS_TOKEN;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successLogout() {
        AuthConstant responseCode = AuthConstant.SUCCESS_LOGOUT;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse notFoundUserName() {
        AuthConstant responseCode = AuthConstant.USERNAME_NOT_FOUND;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse successIssuePassword() {
        AuthConstant responseCode = AuthConstant.SUCCESS_ISSUE_PASSWORD;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public AuthResponse failIssuePassword() {
        AuthConstant responseCode = AuthConstant.FAIL_ISSUE_PASSWORD;
        return new AuthResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }
}

