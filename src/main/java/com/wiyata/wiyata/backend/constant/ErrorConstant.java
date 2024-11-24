package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorConstant {

    METHOD_NOT_ALLOWED("Metode yang diminta tidak diizinkan untuk endpoint ini.", HttpStatus.METHOD_NOT_ALLOWED),
    BAD_REQUEST("Permintaan tidak valid.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Akses tidak sah.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Akses ditolak.", HttpStatus.FORBIDDEN),
    NOT_FOUND("Sumber daya tidak ditemukan.", HttpStatus.NOT_FOUND),
    CONFLICT("Terjadi konflik.", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("Terjadi kesalahan yang tidak terduga.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ErrorConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

