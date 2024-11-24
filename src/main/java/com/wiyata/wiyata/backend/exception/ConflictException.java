package com.wiyata.wiyata.backend.exception;

import com.wiyata.wiyata.backend.constant.ErrorConstant;

public class ConflictException extends CustomException {
    public ConflictException(ErrorConstant errorConstant) {
        super(errorConstant);
    }
}