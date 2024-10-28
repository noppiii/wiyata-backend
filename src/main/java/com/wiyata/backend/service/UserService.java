package com.wiyata.backend.service;

import com.wiyata.backend.model.User;
import com.wiyata.backend.payload.request.LoginRequest;
import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.payload.response.LoginResponse;
import com.wiyata.backend.security.jwt.JwtToken;

import java.io.IOException;

public interface UserService {

    void register(RegisterRequest registerRequest) throws IOException;

    LoginResponse login(LoginRequest loginRequest);

    JwtToken rotateToken(String requestRefreshToken);

    User getUserByEmail(String email);

    void changePassword(String email, String password);

    String logout(String email);
}
