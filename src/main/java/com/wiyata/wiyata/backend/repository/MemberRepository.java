package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserName(String username);

    boolean existsByUserName(String username);

    @Query("SELECT m FROM Member m WHERE m.memberInfo.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Member AS m set m.password = :password WHERE m.userName= :userName")
    int updateMemberPassword(@Param("password") String password, @Param("userName") String userName);

    @Query("SELECT (COUNT(m) > 0) FROM Member m WHERE m.memberProfile.nickname = :nickName")
    boolean existsByNickName(@Param("nickName") String nickName);

    @Query("SELECT m FROM Member AS m WHERE m.id= :id")
    Optional<Member> findMemberId(@Param("id") Long id);
}
