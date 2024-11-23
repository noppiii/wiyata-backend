package com.wiyata.wiyata.backend.entity.location;

import com.wiyata.wiyata.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "MEMBER_ID")
    private Long memberId;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private boolean isPublic;
}
