package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Culture extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private boolean parking;

    private String restDate;

    @Column(length = 1000)
    private String fee;

    private String useTime;

    private String spendTime;

}