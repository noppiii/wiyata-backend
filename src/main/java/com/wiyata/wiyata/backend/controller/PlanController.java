package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Day;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.*;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.location.LocationService;
import com.wiyata.wiyata.backend.service.plan.ConceptService;
import com.wiyata.wiyata.backend.service.plan.DayService;
import com.wiyata.wiyata.backend.service.plan.PlanService;
import com.wiyata.wiyata.backend.service.plan.SelectedLocationService;
import com.wiyata.wiyata.backend.util.HTTPStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wiyata.wiyata.backend.converter.PlanConverter.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/plan")
public class PlanController {

    private final PlanService planService;
    private final ConceptService conceptService;
    private final DayService dayService;
    private final SelectedLocationService selectedLocationService;
    private final LocationService locationService;

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
            String errorMessage = "Anda adalah anggota yang tidak terdaftar.";
            return new UpdateConcept(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            conceptService.updateConcept(planId, createConceptRequest);
            String message = "Konsep telah berhasil dibuat dan diperbarui.";
            return new UpdateConcept(HTTPStatus.Created.getCode(), message);
        }
    }

    @GetMapping("/members/plan/{planId}/concept")
    public ResponseEntity<?> usersConcepts(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberFromPayload = planService.getMemberFromPayload(request);

        if (memberFromPayload.getId() != null) {
            List<String> conceptIdForPlanIdToList = conceptService.findConceptIdForPlanIdToList(planId);
            Map<String, Object> conceptResult = new HashMap<>();
            conceptResult.put("conceptForm", conceptIdForPlanIdToList);
            conceptResult.put("planId", planId);
            return ResponseEntity.ok().body(conceptResult);
        } else {
            throw new IllegalStateException("Anda bukan anggota terdaftar.");
        }
    }

    @PostMapping("/members/plan/{planId}/day")
    public CreateDay createDay(@RequestBody CreateDayRequest createDayRequest, HttpServletRequest request, @PathVariable("planId") Long planId) {
        Member member = planService.getMemberFromPayload(request);
        if (member.getId() == null) {
            String errorMessage = "Anda adalah anggota yang tidak terdaftar.";
            return new CreateDay(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            dayService.createDay(createDayRequest, planId);

            String message = "Hari berhasil dibuat.";

            return new CreateDay(HTTPStatus.Created.getCode(), message);
        }
    }

    @GetMapping("/members/plan/{planId}/day")
    public UserDay getDaysInPlan(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberFromPayload = planService.getMemberFromPayload(request);
        String message = "Hari berhasil dimuat.";

        if (memberFromPayload.getId() != null) {
            List<Day> dayList = dayService.findDayIdForPlanIdToList(planId);
            List<DayRequest> dayDtos = dayList.stream().map(Day::toRequest).collect(toList());
            return new UserDay(HTTPStatus.OK.getCode(), message, dayDtos);
        } else {
            String errorMessage = "Anda adalah anggota yang tidak terdaftar.";

            return new UserDay(HTTPStatus.Unauthorized.getCode(), errorMessage, null);
        }
    }

    @PutMapping("/members/plan/{planId}/day")
    public GetDay updateUserPlanDay(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody CreateDayRequest createDayRequest) {
        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "Anda adalah anggota yang tidak terdaftar.";
            return new GetDay(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            dayService.updateDay(planId, createDayRequest);
            String message = "Hari telah berhasil diubah.";
            return new GetDay(HTTPStatus.Created.getCode(), message);
        }
    }

    @GetMapping("/members/plan/{planId}/selected-location")
    public ResponseEntity usersSelectedLocation(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);
        Plan plan = planService.returnPlan(planId, member);
        List<Long> locationIdList = selectedLocationService.findLocationIdList(plan);
        return ResponseEntity.ok().body(locationService.getMarkAndBlockLocationsFromLocationIds(locationIdList));
    }

    @PostMapping("/members/plan/{planId}/update")
    public UpdatePlan updateUserPlan(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody UserPlanUpdateRequest userPlanUpdateRequest) {
        Member member = planService.getMemberFromPayload(request);
        planService.updateUserPlanContent(planId, member, userPlanUpdateRequest);
        String message = "Rencananya telah berhasil diubah.";
        return new UpdatePlan(HTTPStatus.Created.getCode(), message);
    }

}
