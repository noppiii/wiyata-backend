package com.wiyata.wiyata.backend.payload.request.member;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UpdatePasswordMemberRequest {

    @NotBlank(message = "Password lama tidak boleh kosong.")
    @Size(min = 5, max = 20, message = "Kata sandi lama harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Kata sandi lama harus menggunakan huruf besar, huruf kecil, angka, dan karakter khusus.")
    private String originPassword;

    @NotBlank(message = "Password tidak boleh kosong.")
    @Size(min = 5, max = 20, message = "Kata sandi harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Kata sandi harus menggunakan huruf besar, huruf kecil, angka, dan karakter khusus.")
    private String newPassword;
}