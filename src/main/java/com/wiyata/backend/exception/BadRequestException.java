package com.wiyata.backend.exception;

import com.wiyata.backend.constant.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}