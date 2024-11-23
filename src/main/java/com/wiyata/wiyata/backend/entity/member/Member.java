package com.wiyata.wiyata.backend.entity.member;

import com.wiyata.wiyata.backend.entity.enumerated.Gender;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.entity.plan.UserDirectory;
import com.wiyata.wiyata.backend.entity.location.MemberLocation;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder(builderMethodName = "MemberBuilder")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

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

    @Builder
    public static MemberBuilder builder(MemberSaveRequest memberSaveRequest, String password) {
        return MemberBuilder()
                .userName(memberSaveRequest.getUserName())
                .password(password)
                .emailAuth(false)
                .memberProfile(new MemberProfile(memberSaveRequest.getNickName(), null))
                .memberInfo(new MemberInfo(memberSaveRequest.getBirthday(), Gender.valueOf(memberSaveRequest.getGender()),
                        memberSaveRequest.getEmail()))
                .roles(Collections.singletonList("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
