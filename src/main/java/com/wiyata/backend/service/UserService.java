package com.wiyata.backend.service;

import com.wiyata.backend.model.User;
import com.wiyata.backend.payload.request.LoginRequest;
import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.payload.response.LoginResponse;
import com.wiyata.backend.security.jwt.JwtToken;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserService {

    void register(RegisterRequest registerRequest) throws IOException;

    LoginResponse login(LoginRequest loginRequest);

    Authentication validateAndGetAuthentication(String requestRefreshToken);

    JwtToken rotateToken(String requestRefreshToken);

    void checkLogin(String email);

    User getUserByEmail(String email);

    void changePassword(String email, String password);

    boolean isSnatch(String requestRefreshToken, String currentRefreshToken);

    String logout(String email);
}
