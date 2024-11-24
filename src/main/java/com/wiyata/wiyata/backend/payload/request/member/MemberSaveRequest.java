package com.wiyata.wiyata.backend.payload.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveRequest {

    @NotBlank(message = "Username tidak boleh kosong.")
    private String userName;

    @NotBlank(message = "Password tidak boleh kosong.")
    @Size(min = 5, max = 20, message = "Kata sandi harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Kata sandi harus menggunakan huruf besar, huruf kecil, angka, dan karakter khusus.")
    private String password;

    @NotBlank(message = "Nama asli tidak boleh kosong.")
    private String realName;

    @NotBlank(message = "Nickname tidak boleh kosong.")
    private String nickName;

    @NotBlank(message = "Tanggal lahir tidak boleh kosong.")
    private String birthday;

    @NotBlank(message = "Jenis kelamin tidak boleh kosong.")
    private String gender;

    @NotBlank(message = "Nomor telepon tidak boleh kosong.")
    @Pattern(
            regexp = "^(62|08)[0-9]{8,13}$",
            message = "Nomor telepon harus dimulai dengan 62 atau 08, dan memiliki panjang 10-15 digit."
    )
    private String phoneNum;

    @NotBlank(message = "Email tidak boleh kosong.")
    @Email(message = "Format email tidak valid.")
    private String email;
}
