package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Festival extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String endDate;

    @Column
    private String homepage;

    @Column
    private String place;

    @Column
    private String startDate;

    @Column(length = 1000)
    private String placeInfo;

    @Column(length = 1000)
    private String playTime;

    @Column
    private String program;

    @Column(length = 1000)
    private String fee;

}