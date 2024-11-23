package com.wiyata.wiyata.backend.entity.plan;

import com.wiyata.wiyata.backend.entity.BaseTimeEntity;
import com.wiyata.wiyata.backend.entity.enumerated.PlanComplete;
import com.wiyata.wiyata.backend.entity.enumerated.PlanStatus;
import com.wiyata.wiyata.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plan")
    private List<Day> days = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<SelectedLocation> selectedLocations = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Concept> concepts = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlanItem> planItems = new ArrayList<>();

    private String name;

    private int periods;

    private String depart;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @Enumerated(EnumType.STRING)
    private PlanComplete planComplete;
}
