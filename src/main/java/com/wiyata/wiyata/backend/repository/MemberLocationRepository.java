package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.MemberLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {

    @Query("SELECT m FROM MemberLocation m WHERE m.locationId = :locationId")
    Optional<MemberLocation> findByLocationId(@Param("locationId") Long locationId);

    @Modifying
    @Query("DELETE FROM MemberLocation m WHERE m.locationId = :locationId")
    void deleteMemberLocationByLocationId(@Param("locationId") Long locationId);

    @Query("SELECT (count(m) > 0) FROM MemberLocation m WHERE m.locationId = :locationId")
    boolean existsMemberLocationByLocationId(@Param("locationId") Long locationId);
}
