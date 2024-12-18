package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthConstant {

    SUCCESS_SIGNUP("Pendaftaran pengguna berhasil dilakukan.", HttpStatus.CREATED),
    DUPLICATE_ID("ID sudah digunakan.", HttpStatus.BAD_REQUEST),
    SUCCESS_EMAIL_AUTH("Verifikasi email berhasil dilakukan.", HttpStatus.ACCEPTED),
    FAIL_EMAIL_AUTH("URI verifikasi telah kedaluwarsa atau tidak valid. Silakan coba lagi.", HttpStatus.METHOD_NOT_ALLOWED),
    INVALID_USERNAME_OR_PASSWORD("Username atau password tidak valid.", HttpStatus.BAD_REQUEST),
    SUCCESS_LOGIN("Login berhasil dilakukan.", HttpStatus.ACCEPTED),
    SUCCESS_CREATE_TOKEN("Token berhasil dibuat", HttpStatus.CREATED),
    EXPIRE_TOKEN("Token telah kadaluarsa", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_REFRESH_TOKEN("Refresh token tidak ditemukan.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ACCESS_TOKEN("Access token tidak ditemukan.", HttpStatus.NOT_FOUND),
    SUCCESS_LOGOUT("Logout berhasil dilakukan.", HttpStatus.OK),
    USERNAME_NOT_FOUND("Data user tidak ditemukan", HttpStatus.NOT_FOUND),
    SUCCESS_ISSUE_PASSWORD("Password berhasil diperbaruhi", HttpStatus.ACCEPTED),
    FAIL_ISSUE_PASSWORD("Password gagal diperbaruhi", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    AuthConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}