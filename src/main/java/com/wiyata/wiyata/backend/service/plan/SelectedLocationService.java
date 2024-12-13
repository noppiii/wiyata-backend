package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.entity.plan.Plan;

import java.util.List;

public interface SelectedLocationService {

    List<Long> findLocationIdList(Plan plan);
}
