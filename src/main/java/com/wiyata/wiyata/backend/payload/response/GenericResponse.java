package com.wiyata.wiyata.backend.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    private int status;
    private String message;
    private T result;

    public GenericResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
