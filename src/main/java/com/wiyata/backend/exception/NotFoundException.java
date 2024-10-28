package com.wiyata.backend.exception;

import com.wiyata.backend.constant.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
