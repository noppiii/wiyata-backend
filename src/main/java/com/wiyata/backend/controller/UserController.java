package com.wiyata.backend.controller;

import com.wiyata.backend.payload.request.LoginRequest;
import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.payload.response.LoginResponse;
import com.wiyata.backend.service.EmailService;
import com.wiyata.backend.service.UserService;
import com.wiyata.backend.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


}
