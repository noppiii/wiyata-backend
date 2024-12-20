package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.PlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UserPlanUpdateRequest;
import com.wiyata.wiyata.backend.payload.response.plan.PlanResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface PlanService {

    Plan createMemberPlan(CreatePlanRequest planRequest, Member member);

    PlanRequest getOnePlanRequest(Long planId, Member member);

    Member getMemberFromPayload(HttpServletRequest request);

    void finishedPlan(Long planId);

    void unFinishedPlan(Long planId);

    Plan returnPlan(Long planId, Member member);

    void updateUserPlanContent(Long planId, Member member, UserPlanUpdateRequest userPlanUpdateRequest);
}

