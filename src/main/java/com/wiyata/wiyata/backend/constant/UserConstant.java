package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserConstant {

    SUCCESS_SIGNUP("Pendaftaran pengguna berhasil dilakukan.", HttpStatus.CREATED),
    DUPLICATE_ID("ID sudah digunakan.", HttpStatus.BAD_REQUEST),
    SUCCESS_EMAIL_AUTH("Verifikasi email berhasil dilakukan.", HttpStatus.ACCEPTED),
    FAIL_EMAIL_AUTH("URI verifikasi telah kedaluwarsa atau tidak valid. Silakan coba lagi.", HttpStatus.METHOD_NOT_ALLOWED);

    private final String message;
    private final HttpStatus status;

    UserConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}