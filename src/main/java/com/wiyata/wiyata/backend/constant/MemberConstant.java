package com.wiyata.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberConstant {

    DUPLICATE_USERNAME("ID atau username sudah digunakan", HttpStatus.CONFLICT),
    CAN_USE_USERNAME("ID atau username ini tersedia", HttpStatus.OK),
    DUPLICATE_NICKNAME("Nick name sudah digunakan", HttpStatus.CONFLICT),
    CAN_USE_NICKNAME("Nick name ini tersedia", HttpStatus.OK),
    SUCCESS_GET_MEMBER_PAGE("Berhasil mendapatkan halaman user", HttpStatus.OK),
    SUCCESS_GET_MEMBER_EDIT_PAGE("Berhasil mendapatkan halaman edit user", HttpStatus.OK),
    SUCCESS_CHANGE_MEMBER_IMG("Berhasil memperbaruhi gambar member", HttpStatus.OK),
    FAIL_CHANGE_MEMBER_IMG("Gagal memberbaruhi gambar member", HttpStatus.BAD_REQUEST),
    SUCCESS_EDIT_MEMBER_INFO("Berhasil memperbaruhi member info", HttpStatus.OK),
    SUCCESS_EDIT_MEMBER_PROFILE("Berhasil memperbaruhi member profile", HttpStatus.OK),
    SUCCESS_EDIT_PASSWORD("Berhasil memperbaruhi password", HttpStatus.OK),
    FAIL_EDIT_PASSWORD("Gagal memperbaruhi password", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final HttpStatus status;

    MemberConstant(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
