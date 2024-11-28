package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.LoginRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity<AuthResponse> memberSignUp(MemberSaveRequest signUpRequest);

    ResponseEntity<AuthResponse> confirmEmail(EmailAuthRequest emailRequest);

    ResponseEntity<AuthResponse> memberLogin(LoginRequest request, HttpServletResponse response);

    ResponseEntity<AuthResponse> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<AuthResponse> getMemberInfo(HttpServletRequest request);

    ResponseEntity<AuthResponse> memberLogout(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<AuthResponse> getTmpPassword(Map<String, String> userInfo);
}
