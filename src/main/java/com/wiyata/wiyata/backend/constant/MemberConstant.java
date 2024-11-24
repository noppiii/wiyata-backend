package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberConstant {

    DUPLICATE_USERNAME("ID atau username sudah digunakan", HttpStatus.CONFLICT),
    CAN_USE_USERNAME("ID atau username ini tersedia", HttpStatus.OK);

    private final String message;
    private final HttpStatus status;

    MemberConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
