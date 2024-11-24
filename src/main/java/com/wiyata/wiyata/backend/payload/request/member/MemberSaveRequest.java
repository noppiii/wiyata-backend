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
    @Size(min = 8, message = "Password harus memiliki panjang minimal 8 karakter.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?/{}~])(?=.*[a-zA-Z]).{8,}$",
            message = "Password harus mengandung minimal 1 huruf kapital, 1 karakter khusus, dan panjang minimal 8 karakter."
    )
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
