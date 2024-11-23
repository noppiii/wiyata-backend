package com.wiyata.wiyata.backend.payload.response;

import com.wiyata.wiyata.backend.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
