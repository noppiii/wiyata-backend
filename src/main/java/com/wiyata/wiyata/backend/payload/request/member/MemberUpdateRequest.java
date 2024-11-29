package com.wiyata.wiyata.backend.payload.request.member;

import com.wiyata.wiyata.backend.entity.enumerated.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateRequest {

    @NotBlank(message = "Nickname tidak boleh kosong.")
    @Size(max = 50, message = "Nickname tidak boleh lebih dari 50 karakter.")
    private String nickName;

    @NotBlank(message = "Bio tidak boleh kosong.")
    @Size(max = 255, message = "Bio tidak boleh lebih dari 255 karakter.")
    private String bio;

    @NotBlank(message = "Birthday tidak boleh kosong.")
    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "Format tanggal lahir harus YYYY-MM-DD."
    )
    private String birthday;

    @NotNull(message = "Gender tidak boleh kosong.")
    private Gender gender;

    @Email(message = "Email tidak valid.")
    @NotBlank(message = "Email tidak boleh kosong.")
    private String email;
}
