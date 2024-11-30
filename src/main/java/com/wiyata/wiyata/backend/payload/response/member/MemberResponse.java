package com.wiyata.wiyata.backend.payload.response.member;

import com.wiyata.wiyata.backend.constant.AuthConstant;
import com.wiyata.wiyata.backend.constant.MemberConstant;
import com.wiyata.wiyata.backend.entity.member.MemberInfo;
import com.wiyata.wiyata.backend.entity.member.MemberProfile;
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
public class MemberResponse {

    private int status;
    private String message;
    private Object result;

    public MemberResponse duplicateUsername() {
        MemberConstant responseCode = MemberConstant.DUPLICATE_USERNAME;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse canUseUsername() {
        MemberConstant responseCode = MemberConstant.CAN_USE_USERNAME;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse duplicateNickName() {
        MemberConstant responseCode = MemberConstant.DUPLICATE_NICKNAME;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse canUseNickName() {
        MemberConstant responseCode = MemberConstant.CAN_USE_NICKNAME;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse successGetMemberPage(String nickname, String bio) {
        MemberConstant responseCode = MemberConstant.SUCCESS_GET_MEMBER_PAGE;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("nickname", nickname);
        resultData.put("bio", bio);
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse successGetMemberEditPage(MemberProfile memberProfile, MemberInfo memberInfo) {
        MemberConstant responseCode = MemberConstant.SUCCESS_GET_MEMBER_EDIT_PAGE;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("memberProfile", memberProfile);
        resultData.put("memberInfo", memberInfo);
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse successChangeMemberImg() {
        MemberConstant responseCode = MemberConstant.SUCCESS_CHANGE_MEMBER_IMG;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse failChangeMemberImg() {
        MemberConstant responseCode = MemberConstant.FAIL_CHANGE_MEMBER_IMG;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse successEditMemberInfo(MemberProfile memberProfile, MemberInfo memberInfo) {
        MemberConstant responseCode = MemberConstant.SUCCESS_EDIT_MEMBER_INFO;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("memberProfile", memberProfile);
        resultData.put("memberInfo", memberInfo);
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse successEditMemberProfile(MemberProfile memberProfile) {
        MemberConstant responseCode = MemberConstant.SUCCESS_GET_MEMBER_PAGE;
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("memberProfile", memberProfile);
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), resultData);
    }

    public MemberResponse successEditMemberPassword() {
        MemberConstant responseCode = MemberConstant.SUCCESS_EDIT_PASSWORD;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }

    public MemberResponse failEditMemberPassword() {
        MemberConstant responseCode = MemberConstant.FAIL_EDIT_PASSWORD;
        return new MemberResponse(responseCode.getStatus().value(), responseCode.getMessage(), null);
    }
}
