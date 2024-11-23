package com.wiyata.wiyata.backend.entity.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationInfo {

    @Id
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private String summary;

    @Column(length = 2000)
    private String report;

    private String address;
}
