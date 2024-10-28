package com.wiyata.backend.controller;

import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.service.EmailService;
import com.wiyata.backend.service.UserService;
import com.wiyata.backend.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "users", description = "Users API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final EmailService emailService;

    @Operation(summary = "Register User", description = "Daftar dengan memasukkan alamat email, password, nama, dan [gambar profil] Anda.")
    @PostMapping(value = "/register")
    public ResponseEntity<Void> join(@ModelAttribute @Valid RegisterRequest registerRequest) throws IOException {

        userService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

}
