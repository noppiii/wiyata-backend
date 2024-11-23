package com.wiyata.wiyata.backend.entity.plan;

import com.wiyata.wiyata.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDirectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_directory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "userDirectory", cascade = CascadeType.ALL)
    private List<PlanItem> planItemList;

    private String directoryName;
}
