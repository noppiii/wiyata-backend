package com.wiyata.wiyata.backend.payload.request.plan;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DayFormRequest {

    private List<List<OneDayOneLocationFormRequest>> travelDay;

}
