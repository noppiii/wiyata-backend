package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.plan.Concept;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query("SELECT c FROM Concept c WHERE c.plan = :plan")
    List<Concept> findConceptByPlan(@Param("plan") Plan plan);

}
