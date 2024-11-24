package com.wiyata.wiyata.backend.payload.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email wajib diisi.")
    private String username;

    @NotBlank(message = "Kata sandi wajib diisi.")
    @Size(min = 5, max = 20, message = "Kata sandi harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Kata sandi harus menggunakan huruf besar, huruf kecil, angka, dan karakter khusus.")
    private String password;
}
