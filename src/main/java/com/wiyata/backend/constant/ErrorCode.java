package com.wiyata.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400
    EMPTY_REFRESH_TOKEN("RefreshToken diperlukan.", HttpStatus.BAD_REQUEST),
    EMPTY_EMAIL("Email diperlukan.", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_TYPE("Jenis file tidak valid.", HttpStatus.BAD_REQUEST),
    AWS_FAIL_UPLOAD("Gagal mengunggah ke AWS S3!", HttpStatus.CONFLICT),

    NOT_MATCH_EMAIL_TOKEN("Token verifikasi email tidak cocok.", HttpStatus.CONFLICT),
    EXPIRED_EMAIL_TOKEN("Token verifikasi email tidak ditemukan (kedaluwarsa).", HttpStatus.NOT_FOUND),
    OVER_COUNT_TAGS("Jumlah maksimal tag minat adalah 5.", HttpStatus.CONFLICT),

    // 401
    LOGOUTED_TOKEN("Token sudah diproses untuk logout.", HttpStatus.UNAUTHORIZED),
    SNATCH_TOKEN("Terjadi deteksi pencurian Refresh Token, logout diproses.", HttpStatus.UNAUTHORIZED),
    INVALID_TYPE_TOKEN("Jenis token harus Bearer.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_ACCESS_TOKEN("AccessToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_REFRESH_TOKEN("RefreshToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    EMPTY_AUTHORITY("Informasi otorisasi diperlukan.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("AccessToken tidak valid.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("RefreshToken tidak valid.", HttpStatus.UNAUTHORIZED),
    INVALID_CURRENT_PASSWORD("Password saat ini tidak cocok!", HttpStatus.UNAUTHORIZED),
    INVALID_NEW_PASSWORD("Password baru tidak cocok!", HttpStatus.UNAUTHORIZED),

    // 403
    FORBIDDEN_CREATE("Tidak memiliki izin untuk membuat.", HttpStatus.FORBIDDEN),
    FORBIDDEN_DELETE("Tidak memiliki izin untuk menghapus.", HttpStatus.FORBIDDEN),
    FORBIDDEN_UPDATE("Tidak memiliki izin untuk memperbarui.", HttpStatus.FORBIDDEN),

    // 404
    NOT_FOUND_USER("User tidak ditemukan.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ITEM("Produk tidak ditemukan.", HttpStatus.NOT_FOUND),
    NOT_FOUND_PROVIDER("Platform login sosial ini tidak didukung.", HttpStatus.NOT_FOUND),
    NOT_FOUND_EMAIL_TOKEN("Token verifikasi email tidak ditemukan.", HttpStatus.NOT_FOUND),

    ALREADY_EXISTS("User sudah ada.", HttpStatus.CONFLICT),

    // Terkait forum
    BAD_REQUEST("Permintaan tidak valid.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Silakan login.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Tidak memiliki izin akses.", HttpStatus.FORBIDDEN),
    ARTICLE_NOT_FOUND("Artikel tidak ditemukan.", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("Komentar tidak ditemukan.", HttpStatus.NOT_FOUND),
    INTEREST_ARTICLE_ALREADY_EXISTS("Artikel ini sudah disukai.", HttpStatus.BAD_REQUEST),
    INTEREST_ARTICLE_NOT_FOUND("Artikel ini belum disukai.", HttpStatus.NOT_FOUND),
    UPLOAD_FAILED("Gagal mengunggah file.", HttpStatus.INTERNAL_SERVER_ERROR),

    // Notifikasi
    NOTIFICATION_NOT_FOUND("Notifikasi tidak ditemukan.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
