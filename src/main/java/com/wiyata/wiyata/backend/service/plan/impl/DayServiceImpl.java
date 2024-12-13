package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.enumerated.PlanComplete;
import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.entity.plan.Day;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreateDayRequest;
import com.wiyata.wiyata.backend.payload.request.plan.DayFormRequest;
import com.wiyata.wiyata.backend.repository.DayRepository;
import com.wiyata.wiyata.backend.repository.LocationRepository;
import com.wiyata.wiyata.backend.repository.PlanRepository;
import com.wiyata.wiyata.backend.service.plan.DayService;
import com.wiyata.wiyata.backend.service.plan.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {

    private final PlanRepository planRepository;
    private final PlanService planService;
    private final LocationRepository locationRepository;
    private final DayRepository dayRepository;

    @Override
    @Transactional
    public List<Day> createDay(CreateDayRequest createDayRequest, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        if (plan.getPlanComplete() == PlanComplete.FINISHED) {
            planService.unFinishedPlan(planId);
        }
        planService.finishedPlan(planId);
        DayFormRequest dayForm = createDayRequest.getDayForm();

        ArrayList<Day> dayList = new ArrayList();

        for (int i = 0; i < dayForm.getTravelDay().size(); i++) {
            for (int j = 0; j < dayForm.getTravelDay().get(i).size(); j++) {

                Optional<Location> optionalLocation = locationRepository.findLocationById(dayForm.getTravelDay().get(i).get(j).getLocationId());

                Day day = Day.builder()
                        .location(optionalLocation.orElseThrow())
                        .copyLocationId(dayForm.getTravelDay().get(i).get(j).getCopyLocationId())
                        .plan(plan)
                        .days(dayForm.getTravelDay().get(i).get(j).getDays())
                        .movingData(dayForm.getTravelDay().get(i).get(j).getMovingData())
                        .build();

                dayList.add(day);
            }
        }

        return dayRepository.saveAll(dayList);
    }
}
