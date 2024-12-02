package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.MemberLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {
}
