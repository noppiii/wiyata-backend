package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.LoginRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<MemberResponse> memberSignUp(MemberSaveRequest signUpRequest);

    ResponseEntity<MemberResponse> confirmEmail(EmailAuthRequest emailRequest);

    ResponseEntity<MemberResponse> memberLogin(LoginRequest request, HttpServletResponse response);
}
