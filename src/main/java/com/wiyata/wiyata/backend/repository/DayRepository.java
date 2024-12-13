package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.plan.Day;
import com.wiyata.wiyata.backend.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {

    @Query("SELECT d FROM Day d WHERE d.plan = :plan")
    List<Day> findDaysByPlan(@Param("plan") Plan plan);
}
