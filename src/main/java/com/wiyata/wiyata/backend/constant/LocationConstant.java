package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LocationConstant {

    LOCATION_NOT_FOUND("Lokasi tidak ditemukan", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    LocationConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
