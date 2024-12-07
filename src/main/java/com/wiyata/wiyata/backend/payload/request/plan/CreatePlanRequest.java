package com.wiyata.wiyata.backend.payload.request.plan;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePlanRequest {

    private PlanFormRequest planForm;
    private ConceptFormRequest conceptForm;
    private SelectedLocationFormRequest selectedLocationForm;
    private DayFormRequest dayForm;
}
