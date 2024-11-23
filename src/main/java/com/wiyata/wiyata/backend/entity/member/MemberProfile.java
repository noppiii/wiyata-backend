package com.wiyata.wiyata.backend.entity.member;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class MemberProfile {

    @NotNull
    private String nickname;

    private String bio;

    protected MemberProfile() {

    }
}
