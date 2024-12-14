package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.entity.plan.SelectedLocation;
import com.wiyata.wiyata.backend.payload.request.plan.UpdateConceptRequest;
import com.wiyata.wiyata.backend.repository.LocationRepository;
import com.wiyata.wiyata.backend.repository.PlanRepository;
import com.wiyata.wiyata.backend.repository.SelectedLocationRepository;
import com.wiyata.wiyata.backend.service.plan.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationServiceImpl implements SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;
    private final PlanRepository planRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<Long> findLocationIdList(Plan plan) {
        return selectedLocationRepository.findLocationIdByPlanId(plan);
    }

    @Override
    public List<SelectedLocation> updateSelectedLocation(Long id, UpdateConceptRequest updateConceptRequest) {
        Plan plan = planRepository.findPlanById(id).orElseThrow();
        removeSelectedLocation(plan);
        return createSelectedLocation(updateConceptRequest, id);
    }

    @Override
    public List<SelectedLocation> createSelectedLocation(UpdateConceptRequest updateConceptRequest, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        ArrayList<SelectedLocation> selectedLocationList = new ArrayList<>();

        for (int i = 0; i < updateConceptRequest.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Optional<Location> OptionalLocation = locationRepository.findLocationById(updateConceptRequest.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(plan)
                    .location(OptionalLocation.orElseThrow())
                    .build();
            selectedLocationList.add(selectedLocation);
        }

        return selectedLocationRepository.saveAll(selectedLocationList);
    }

    @Override
    public void removeSelectedLocation(Plan plan) {
        List<SelectedLocation> selectedLocations = selectedLocationRepository.findSelectedLocationByPlanId(plan);
        if (selectedLocations == null || selectedLocations.isEmpty()) {
            return;
        }
        selectedLocations.forEach(selectedLocationRepository::delete);
    }
}
