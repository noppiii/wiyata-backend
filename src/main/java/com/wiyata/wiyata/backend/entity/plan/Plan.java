package com.wiyata.wiyata.backend.entity.plan;

import com.wiyata.wiyata.backend.entity.BaseTimeEntity;
import com.wiyata.wiyata.backend.entity.enumerated.PlanComplete;
import com.wiyata.wiyata.backend.entity.enumerated.PlanStatus;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.payload.request.plan.PlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UserPlanUpdateRequest;
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

    public PlanRequest toRequest() {
        return PlanRequest.builder()
                .planId(id)
                .depart(depart)
                .name(name)
                .periods(periods)
                .build();
    }

    public void finished() {
        if (getPlanComplete() == PlanComplete.FINISHED) {
            throw new IllegalStateException("Ini adalah rencana yang telah selesai.");
        }

        this.planComplete = PlanComplete.FINISHED;
    }


    public void unFinished() {
        this.planComplete = PlanComplete.UNFINISHED;
    }

    public void updatePlan(UserPlanUpdateRequest userPlanUpdateRequest) {
        this.depart = userPlanUpdateRequest.getDepart();
        this.name = userPlanUpdateRequest.getName();
        this.periods = userPlanUpdateRequest.getPeriods();
        this.thumbnail = userPlanUpdateRequest.getThumbnail();
    }
}
