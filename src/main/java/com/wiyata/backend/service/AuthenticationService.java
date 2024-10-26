package com.wiyata.backend.service;

import com.nimbusds.openid.connect.sdk.LogoutRequest;
import com.wiyata.backend.payload.request.*;
import com.wiyata.backend.payload.response.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest);
    ResponseEntity<?> verifyUserRegistration(RegisterVerifyRequest registerVerifyRequest);
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    ResponseEntity<?> resendOtp(ForgotPasswordRequest forgotPasswordRequest);
    ResponseEntity<?> verifyOtp(RegisterVerifyRequest registerVerifyRequest);
    ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);
    ResponseEntity<?> myProfile(ForgotPasswordRequest forgotPasswordRequest);
}
