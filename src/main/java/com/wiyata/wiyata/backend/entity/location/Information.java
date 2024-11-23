package com.wiyata.wiyata.backend.entity.location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private String summary;

    @Column(length = 2000)
    private String report;

    private String image1;

    private String image2;

    private String tel;

}
