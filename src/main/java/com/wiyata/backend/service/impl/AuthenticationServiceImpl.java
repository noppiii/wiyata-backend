package com.wiyata.backend.service.impl;

import com.wiyata.backend.payload.request.*;
import com.wiyata.backend.payload.response.RegisterRequest;
import com.wiyata.backend.repository.UserRepository;
import com.wiyata.backend.service.AuthenticationService;
import com.wiyata.backend.service.AuthEmailService;
import com.wiyata.backend.service.JwtService;
import com.wiyata.backend.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final AuthEmailService authEmailService;
    private final JwtService jwtService;
    private final CacheManager cacheManager;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> verifyUserRegistration(RegisterVerifyRequest registerVerifyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> resendOtp(ForgotPasswordRequest forgotPasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> verifyOtp(RegisterVerifyRequest registerVerifyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> myProfile(ForgotPasswordRequest forgotPasswordRequest) {
        return null;
    }
}
