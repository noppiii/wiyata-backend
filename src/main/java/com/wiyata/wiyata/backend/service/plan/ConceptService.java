package com.wiyata.wiyata.backend.service.plan;

import com.wiyata.wiyata.backend.entity.plan.Concept;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.payload.request.plan.CreateConceptRequest;
import com.wiyata.wiyata.backend.payload.request.plan.UpdateConceptRequest;

import java.util.List;

public interface ConceptService {

    List<Concept> createConcept(CreateConceptRequest createConceptRequest, Long planId);

    void removeConcept(Plan plan);

    List<Concept> updateConcept(Long planId, CreateConceptRequest createConceptRequest);
}
