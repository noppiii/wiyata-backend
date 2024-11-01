package com.wiyata.backend.controller;

import com.wiyata.backend.constant.ErrorCode;
import com.wiyata.backend.exception.BadRequestException;
import com.wiyata.backend.payload.request.LoginRequest;
import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.payload.response.LoginResponse;
import com.wiyata.backend.security.jwt.JwtToken;
import com.wiyata.backend.service.EmailService;
import com.wiyata.backend.service.UserService;
import com.wiyata.backend.service.impl.UserServiceImpl;
import com.wiyata.backend.util.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "users", description = "Users API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @Operation(summary = "Register User", description = "Daftar dengan memasukkan alamat email, password, nama, dan [gambar profil] Anda.")
    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) throws IOException {
        userService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Login", description = "Masukkan email dan kata sandi untuk login.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "refreshToken", description = "Menerbitkan ulang refreshToken yang sudah kadaluwarsa.")
    @GetMapping("/rotate")
    public JwtToken rotateToken(HttpServletRequest request) {
        String refreshToken = JwtTokenUtils.extractBearerToken(request.getHeader("refreshToken"));

        if (refreshToken.isBlank()) {
            throw new BadRequestException(ErrorCode.EMPTY_REFRESH_TOKEN);
        }
        return userService.rotateToken(refreshToken);
    }

    @Operation(summary = "Logout", description = "Logout akun saat ini.")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(userService.logout(username));
    }
}
