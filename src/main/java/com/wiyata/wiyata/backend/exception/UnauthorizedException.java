package com.wiyata.wiyata.backend.exception;

import com.wiyata.wiyata.backend.constant.ErrorConstant;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorConstant errorConstant) {
        super(errorConstant);
    }
}