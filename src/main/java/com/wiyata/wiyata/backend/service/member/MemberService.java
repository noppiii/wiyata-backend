package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.MemberResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<MemberResponse> memberSignUp(MemberSaveRequest signUpRequest);
}
