package com.wiyata.backend.exception;

import com.wiyata.backend.constant.ErrorCode;

public class DuplicateException extends CustomException {
    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}