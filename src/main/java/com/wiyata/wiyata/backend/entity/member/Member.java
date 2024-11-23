package com.wiyata.wiyata.backend.entity.member;

import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.entity.plan.UserDirectory;
import com.wiyata.wiyata.backend.entity.location.MemberLocation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(builderMethodName = "MemberBuilder")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userName;

    private String password;

    private Boolean emailAuth;

    @Embedded
    private MemberProfile memberProfile;

    @Embedded
    private MemberInfo memberInfo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> memberLocation = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<UserDirectory> userDirectories = new ArrayList<>();
}
