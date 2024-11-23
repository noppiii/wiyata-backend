package com.wiyata.wiyata.backend.repository.member;

import com.wiyata.wiyata.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserName(String username);

    boolean existsByUserName(String username);
}
