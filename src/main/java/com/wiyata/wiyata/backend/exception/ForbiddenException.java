package com.wiyata.wiyata.backend.exception;

import com.wiyata.wiyata.backend.constant.ErrorConstant;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorConstant errorConstant) {
        super(errorConstant);
    }
}
