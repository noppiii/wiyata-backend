package com.wiyata.wiyata.backend.payload.request.plan;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConceptFormRequest {

    private List<String> concept;
}
