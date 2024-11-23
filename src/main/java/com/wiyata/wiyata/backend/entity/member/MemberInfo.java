package com.wiyata.wiyata.backend.entity.member;

import com.wiyata.wiyata.backend.entity.enumerated.Gender;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@Setter
public class MemberInfo {

    private String brithday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String email;
}
