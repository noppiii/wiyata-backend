package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PlanConstant {

    SUCCESS_CREATE_PLAN(HttpStatus.CREATED, "Berhasil membuat plan");

    private final HttpStatus status;
    private final String message;

    PlanConstant(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
