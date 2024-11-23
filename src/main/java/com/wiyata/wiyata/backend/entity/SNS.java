package com.wiyata.wiyata.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SNS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long FKid;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @Column
    private String homepage;

    @Builder
    public SNS(String instagram, String facebook, String homepage) {
        this.instagram = instagram;
        this.facebook = facebook;
        this.homepage = homepage;
    }
}