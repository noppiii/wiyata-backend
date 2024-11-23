package com.wiyata.wiyata.backend.entity.member;

import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder(builderMethodName = "EmailAuthBuilder")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String uuid;

    private Boolean expired;

    private LocalDateTime expireDate;

    @Builder
    public static EmailAuthBuilder builder(MemberSaveRequest memberSaveRequest) {
        return EmailAuthBuilder()
                .email(memberSaveRequest.getEmail())
                .uuid(UUID.randomUUID().toString())
                .expired(false)
                .expireDate(LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME));
    }

}
