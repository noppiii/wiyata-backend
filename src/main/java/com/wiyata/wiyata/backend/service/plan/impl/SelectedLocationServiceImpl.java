package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.repository.SelectedLocationRepository;
import com.wiyata.wiyata.backend.service.plan.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationServiceImpl implements SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;

    @Override
    public List<Long> findLocationIdList(Plan plan) {
        return selectedLocationRepository.findLocationIdByPlanId(plan);
    }
}
