package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreatePlanRequest;
import com.wiyata.wiyata.backend.payload.request.plan.PlanRequest;
import com.wiyata.wiyata.backend.payload.response.plan.PlanResponse;
import com.wiyata.wiyata.backend.repository.MemberRepository;
import com.wiyata.wiyata.backend.repository.PlanRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenProvider;
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

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanResponse planResponse;

    @Override
    public Plan createMemberPlan(CreatePlanRequest planRequest, Member member) {
        Plan plan = planRequest.getPlanForm().toEntity(member);
        Plan savedPlan = planRepository.save(plan);

        return savedPlan;
    }

    @Override
    public PlanRequest getOnePlanRequest(Long planId, Member member) {
        return planRepository.findPlanByMember(planId, member).orElseThrow().toRequest();
    }

    @Override
    public Member getMemberFromPayload(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String userName = jwtTokenProvider.getUserName(token);
        return memberRepository.findByUserName(userName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void finishedPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        plan.finished();
    }

    @Override
    public void unFinishedPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        plan.unFinished();
    }
}
