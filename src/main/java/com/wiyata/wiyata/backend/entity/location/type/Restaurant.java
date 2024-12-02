package com.wiyata.wiyata.backend.entity.location.type;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends TypeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String popularMenu;

    @Column
    private String openTime;

    @Column
    private boolean packing;

    @Column
    private boolean parking;

    @Column
    private String restDate;

    @Column(length = 2000)
    private String menu;

}
