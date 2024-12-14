package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.entity.plan.SelectedLocation;
import com.wiyata.wiyata.backend.payload.request.plan.CreateConceptRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UpdateConceptRequest;

import java.util.List;

public interface SelectedLocationService {

    List<Long> findLocationIdList(Plan plan);

    List<SelectedLocation> updateSelectedLocation(Long id, UpdateConceptRequest updateConceptRequest);

    List<SelectedLocation> createSelectedLocation(UpdateConceptRequest updateConceptRequest, Long planId);

    void removeSelectedLocation(Plan plan);
}
