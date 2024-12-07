package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
