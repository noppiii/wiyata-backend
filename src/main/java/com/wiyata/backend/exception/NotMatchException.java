package com.wiyata.backend.exception;

import com.wiyata.backend.constant.ErrorCode;

public class NotMatchException extends CustomException {
    public NotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}