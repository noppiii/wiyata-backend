package com.wiyata.wiyata.backend.payload.request.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

@Getter
@Setter
public class EmailAuthRequest {

    @NotBlank(message = "Email tidak boleh kosong.")
    @Email(message = "Format email tidak valid.")
    private String email;

    @NotBlank(message = "UUID tidak boleh kosong.")
    private String uuid;

    @NotNull(message = "Status kedaluwarsa (expired) tidak boleh null.")
    private boolean expired;

    @NotNull(message = "Tanggal kedaluwarsa tidak boleh null.")
    @Future(message = "Tanggal kedaluwarsa harus di masa depan.")
    private LocalDateTime expireDate;
}
