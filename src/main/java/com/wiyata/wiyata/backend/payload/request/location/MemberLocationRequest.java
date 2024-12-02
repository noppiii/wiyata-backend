package com.wiyata.wiyata.backend.payload.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wiyata.wiyata.backend.entity.location.MemberLocation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberLocationRequest {

    @JsonProperty("memberId")
    @NotNull(message = "ID member wajib diisi.")
    private Long memberId;

    @JsonProperty("isPublic")
    @NotNull(message = "Status publikasi wajib diisi.")
    private Boolean isPublic;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public MemberLocation toEntity(Long locationId) {
        return MemberLocation.builder()
                .memberId(memberId)
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }
}