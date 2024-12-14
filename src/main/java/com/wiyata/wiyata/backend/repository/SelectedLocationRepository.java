package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.plan.Plan;
import com.wiyata.wiyata.backend.entity.plan.SelectedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectedLocationRepository extends JpaRepository<SelectedLocation, Long> {

    @Query("SELECT s.location.id FROM SelectedLocation s WHERE s.plan = :plan")
    List<Long> findLocationIdByPlanId(@Param("plan") Plan plan);

    @Query("SELECT s FROM SelectedLocation s WHERE s.plan = :plan")
    List<SelectedLocation> findSelectedLocationByPlanId(@Param("plan") Plan plan);
}
