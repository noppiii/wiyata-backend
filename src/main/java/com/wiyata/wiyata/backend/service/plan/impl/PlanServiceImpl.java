package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.response.plan.PlanResponse;
import com.wiyata.wiyata.backend.repository.MemberRepository;
import com.wiyata.wiyata.backend.repository.PlanRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.plan.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlanServiceImpl implements PlanService {

    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanResponse planResponse;

    @Override
    public ResponseEntity<PlanResponse> createMemberPlan(CreatePlanRequest planRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        Plan plan = planRequest.getPlanForm().toEntity(member);
        Plan savedPlan = planRepository.save(plan);

        return ResponseEntity.status(HttpStatus.OK).body(planResponse.successCreatePlan(savedPlan));
    }
}
