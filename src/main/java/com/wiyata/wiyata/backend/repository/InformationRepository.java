package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
}
