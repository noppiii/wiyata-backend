package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.entity.plan.Day;
import com.wiyata.wiyata.backend.payload.request.plan.CreateDayRequest;

import java.util.List;

public interface DayService {

    List<Day> createDay(CreateDayRequest createDayRequest, Long planId);

    List<Day> findDayIdForPlanIdToList(Long id);
}
