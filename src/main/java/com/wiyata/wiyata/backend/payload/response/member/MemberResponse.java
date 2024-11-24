package com.wiyata.wiyata.backend.payload.response.member;

import com.wiyata.wiyata.backend.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private int status;
    private String message;
    private Object result;

    public MemberResponse successMemberSignUp(String nickname) {
        UserConstant responseCode = UserConstant.SUCCESS_SIGNUP;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);

        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse failMemberSignUp() {
        UserConstant responseCode = UserConstant.DUPLICATE_ID;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse successEmailAuth() {
        UserConstant responseCode = UserConstant.SUCCESS_EMAIL_AUTH;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse failEmailAuth() {
        UserConstant responseCode = UserConstant.FAIL_EMAIL_AUTH;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse invalidUsernameOrPassword() {
        UserConstant responseCode = UserConstant.INVALID_USERNAME_OR_PASSWORD;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse successLogin(String nickname) {
        UserConstant responseCode = UserConstant.SUCCESS_LOGIN;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse successCreateToken() {
        UserConstant responseCode = UserConstant.SUCCESS_CREATE_TOKEN;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse expireToken() {
        UserConstant responseCode = UserConstant.EXPIRE_TOKEN;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse notFoundRefreshToken() {
        UserConstant responseCode = UserConstant.NOT_FOUND_REFRESH_TOKEN;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }
}

