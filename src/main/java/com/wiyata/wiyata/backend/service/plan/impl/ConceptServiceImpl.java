package com.wiyata.wiyata.backend.service.plan.impl;

import com.wiyata.wiyata.backend.entity.plan.Concept;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreateConceptRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UpdateConceptRequest;
import com.wiyata.wiyata.backend.repository.ConceptRepository;
import com.wiyata.wiyata.backend.repository.PlanRepository;
import com.wiyata.wiyata.backend.service.plan.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    private final PlanRepository planRepository;
    private final ConceptRepository conceptRepository;

    @Override
    @Transactional
    public List<Concept> createConcept(CreateConceptRequest createConceptRequest, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        ArrayList<Concept> conceptList = new ArrayList<>();

        for (int i = 0; i < createConceptRequest.getConceptForm().getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
                    .conceptName(createConceptRequest.getConceptForm().getConcept().get(i))
                    .build();

            conceptList.add(concept);
        }

        return conceptRepository.saveAll(conceptList);
    }

    @Override
    public void removeConcept(Plan plan) {
        List<Concept> conceptList = conceptRepository.findConceptByPlan(plan);

        if (conceptList == null || conceptList.isEmpty()) {
            return;
        }
        conceptList.forEach(conceptRepository::delete);
    }

    @Override
    public List<Concept> updateConcept(Long planId, CreateConceptRequest createConceptRequest) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        if (plan.getConcepts() == null || !plan.getConcepts().isEmpty()) {
            removeConcept(plan);
        }
        return createConcept(createConceptRequest, plan.getId());
    }
}
