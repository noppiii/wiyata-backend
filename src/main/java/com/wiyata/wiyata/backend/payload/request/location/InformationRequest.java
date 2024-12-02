package com.wiyata.wiyata.backend.payload.request.location;

import com.wiyata.wiyata.backend.entity.location.Information;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
public class InformationRequest {

    @NotBlank(message = "Ringkasan wajib diisi.")
    private String summary;

    @NotBlank(message = "Laporan wajib diisi.")
    private String report;

    @NotBlank(message = "URL Gambar1 wajib diisi.")
    private String image1;

    private String image2;

    @NotBlank(message = "Nomor telepon wajib diisi.")
    @Pattern(regexp = "^[+]?[0-9\\-\\s]*$", message = "Format nomor telepon tidak valid.")
    private String tel;

    public Information toEntity(Long locationId) {
        return Information.builder()
                .locationId(locationId)
                .summary(summary)
                .report(report)
                .image1(image1)
                .image2(image2)
                .tel(tel)
                .build();
    }
}

