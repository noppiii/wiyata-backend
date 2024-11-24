package com.wiyata.wiyata.backend.exception;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.payload.response.GenericResponse;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorConstant errorConstant;

    public CustomException(ErrorConstant errorConstant) {
        super(errorConstant.getMessage());
        this.errorConstant = errorConstant;
    }

    public GenericResponse<Object> toErrorResponse() {
        Map<String, List<String>> errors = new HashMap<>();

        errors.put("error", List.of(this.errorConstant.getMessage()));
        errors.put("details", List.of(super.getMessage()));

        return new GenericResponse<>(this.errorConstant.getStatus().value(), this.errorConstant.getMessage(), errors
        );
    }
}

