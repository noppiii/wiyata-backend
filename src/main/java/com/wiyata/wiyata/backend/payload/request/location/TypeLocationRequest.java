package com.wiyata.wiyata.backend.payload.request.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeLocationRequest {

    @NotNull(message = "Location ID wajib diisi.")
    private Long locationId;

    @NotNull(message = "Status parkir wajib diisi.")
    private Boolean parking;

    private String restDate;

    @NotBlank(message = "Biaya wajib diisi.")
    private String fee;

    private String useTime;

    private String spendTime;

    private String endDate;

    private String homepage;

    private String place;

    private String startDate;

    private String placeInfo;

    private String playTime;

    private String program;

    private String openPeriod;

    private String reservation;

    private String checkInTime;

    private String checkOutTime;

    private Boolean cooking;

    private Integer numOfRooms;

    private String reservationUrl;

    private String subFacility;

    private String popularMenu;

    private String openTime;

    private Boolean packing;

    private String menu;

}
