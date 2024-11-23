package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserConstant {

    SUCCESS_SIGNUP("User registration completed successfully.", HttpStatus.CREATED),
    DUPLICATE_ID("The ID is already in use.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    UserConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
