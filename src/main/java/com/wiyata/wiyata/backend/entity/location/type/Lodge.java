package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lodge extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private String checkInTime;

    private String checkOutTime;

    private boolean cooking;

    private boolean parking;

    private Integer numOfRooms;

    private String reservationUrl;

    private String subFacility;

}
