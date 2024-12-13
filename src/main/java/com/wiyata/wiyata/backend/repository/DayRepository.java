package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.plan.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
