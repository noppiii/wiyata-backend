package com.wiyata.wiyata.backend.entity.plan;

import com.wiyata.wiyata.backend.entity.enumerated.PlanItemStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_directory_id")
    private UserDirectory userDirectory;

    @Enumerated(EnumType.STRING)
    private PlanItemStatus planItemStatus;
}
