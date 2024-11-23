package com.wiyata.wiyata.backend.payload.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveRequest {

    private String userName;
    private String password;
    private String realName;
    private String nickName;
    private String birthday;
    private String gender;
    private String phoneNum;
    private String email;

}
