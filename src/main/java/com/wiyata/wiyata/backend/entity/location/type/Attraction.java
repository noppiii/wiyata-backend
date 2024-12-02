package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attraction extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private boolean parking;

    @Column
    private String restDate;

    @Column(length = 1000)
    private String useTime;

}
