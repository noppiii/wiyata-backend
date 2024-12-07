package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.response.plan.PlanResponse;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.plan.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/plan")
public class PlanController {

    private final PlanService planService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/members/plan")
    public ResponseEntity<PlanResponse> createPlan(@RequestBody CreatePlanRequest createPlanRequest, HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);
        return planService.createMemberPlan(createPlanRequest, memberId);
    }

}
