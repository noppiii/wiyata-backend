package com.wiyata.wiyata.backend.payload.request.plan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPlanUpdateRequest {

    private String depart;
    private String name;
    private int periods;
    private String thumbnail;
}
