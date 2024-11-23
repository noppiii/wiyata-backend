package com.wiyata.wiyata.backend.entity.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer areaCode;

    @Column(nullable = false)
    private String address1;

    @Column
    private String address2;

    @Embedded
    private Coords coords;

    @Column
    private String image;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private LocationType type;

    @Column
    private Boolean isMember;

    public void setType(LocationType type) {
        this.type = type;
    }

    public LocationType getType() {
        return this.type;
    }

    public String changeName(String name) {
        return this.name = name;
    }
}
