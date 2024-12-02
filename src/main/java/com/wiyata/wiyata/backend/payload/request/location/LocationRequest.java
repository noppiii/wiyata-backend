package com.wiyata.wiyata.backend.payload.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.entity.location.Coords;
import com.wiyata.wiyata.backend.entity.location.Location;
import lombok.*;

import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationRequest {

    @NotBlank(message = "Nama lokasi wajib diisi.")
    private String name;

    @NotBlank(message = "Alamat1 wajib diisi.")
    private String address1;

    private String address2;

    @NotNull(message = "Koordinat wajib diisi.")
    private Coords coords;

    @NotBlank(message = "URL gambar wajib diisi.")
    private String image;

    @NotNull(message = "Kode area wajib diisi.")
    @Min(value = 1000, message = "Kode area harus terdiri dari setidaknya 4 digit.")
    private Integer areaCode;

    @NotNull(message = "Status keanggotaan wajib diisi.")
    @JsonProperty("isMember")
    private Boolean isMember;

    @NotNull(message = "Jenis lokasi wajib diisi.")
    private LocationType type;

    public void setMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public Location toEntity() {
        return Location.builder()
                .name(name)
                .address1(address1)
                .address2(address2)
                .coords(coords)
                .image(image)
                .areaCode(areaCode)
                .type(type)
                .isMember(isMember != null && isMember)
                .build();
    }
}
