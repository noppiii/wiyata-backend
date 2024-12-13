package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT p FROM Plan p WHERE p.id = :planId AND p.member = :member")
    Optional<Plan> findPlanByMember(@Param("planId") Long planId, @Param("member") Member member);

    @Query("SELECT p FROM Plan p WHERE p.id = :planId")
    Optional<Plan> findPlanById(@Param("planId") Long planId);

}
