package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Leports extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column(length = 1000)
    private String openPeriod;

    @Column
    private boolean parking;

    @Column
    private String reservation;

    @Column
    private String restDate;

    @Column(length = 1000)
    private String fee;

    @Column(length = 1000)
    private String useTime;

}
