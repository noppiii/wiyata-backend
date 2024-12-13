package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.payload.request.plan.CreateConceptRequest;
import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.PlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UpdateConceptRequest;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.plan.ConceptService;
import com.wiyata.wiyata.backend.service.plan.PlanService;
import com.wiyata.wiyata.backend.util.HTTPStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.wiyata.wiyata.backend.converter.PlanConverter.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/plan")
public class PlanController {

    private final PlanService planService;
    private final ConceptService conceptService;

    @PostMapping("/members/plan")
    public CreatePlan createPlan(@RequestBody CreatePlanRequest createPlanRequest, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        Long planId = planService.createMemberPlan(createPlanRequest, member).getId();

        String message = "Berhasil membuat plan";

        return new CreatePlan(HTTPStatus.Created.getCode(), message, planId);
    }


    @PostMapping("/members/plan/{planId}")
    public UserPlan getUserPlans(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        PlanRequest planRequest = planService.getOnePlanRequest(planId, member);

        String message = "Plan berhasil dimuat";

        return new UserPlan(HTTPStatus.Created.getCode(), message, planRequest);
    }

    @PostMapping("/members/plan/{planId}/concept")
    public UpdateConcept updateUserPlanConcept(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody CreateConceptRequest createConceptRequest) {

        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new UpdateConcept(HTTPStatus.Unauthorized.getCode(), errorMessage);

        } else {
            conceptService.updateConcept(planId, createConceptRequest);

            String message = "Konsep telah berhasil dibuat dan diperbarui.";

            return new UpdateConcept(HTTPStatus.Created.getCode(), message);
        }
    }
}
