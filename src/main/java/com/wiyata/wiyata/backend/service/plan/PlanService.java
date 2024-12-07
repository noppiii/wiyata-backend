package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.response.plan.PlanResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface PlanService {
    ResponseEntity<PlanResponse> createMemberPlan(CreatePlanRequest planRequest, Long memberId);
}

