package com.wiyata.backend.exception;


import com.wiyata.backend.constant.ErrorCode;

public class InvalidException extends CustomException {
    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
