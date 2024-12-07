package com.wiyata.wiyata.backend.payload.request.plan;

import com.wiyata.wiyata.backend.entity.enumerated.PlanComplete;
import com.wiyata.wiyata.backend.entity.enumerated.PlanStatus;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanFormRequest {

    private String depart;
    private String name;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;
    private PlanComplete planComplete;

    public Plan toEntity(Member member) {
        return Plan.builder()
                .depart(depart)
                .member(member)
                .name(name)
                .periods(periods)
                .planStatus(PlanStatus.MAIN)
                .thumbnail(thumbnail)
                .planComplete(PlanComplete.UNFINISHED)
                .build();
    }
}
