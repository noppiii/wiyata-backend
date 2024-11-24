package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.payload.request.member.LoginRequest;
import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.AuthResponse;
import com.wiyata.wiyata.backend.service.member.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody MemberSaveRequest request) {
        return authService.memberSignUp(request);
    }

    @GetMapping("/email")
    public ResponseEntity<AuthResponse> confirmEmail(@Valid @ModelAttribute EmailAuthRequest request) {
        return authService.confirmEmail(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.memberLogin(request, response);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.memberRefreshToAccess(request, response);
    }

    @GetMapping("/status")
    public ResponseEntity<AuthResponse> getInfo(HttpServletRequest request) {
        return authService.getMemberInfo(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        return authService.memberLogout(request, response);
    }
}
