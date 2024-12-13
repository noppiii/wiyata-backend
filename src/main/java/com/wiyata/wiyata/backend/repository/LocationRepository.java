package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.repository.customQuery.LocationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

    @Query("SELECT l FROM Location l WHERE l.isMember = false")
    List<Location> findAllByIsMemberFalse();

    @Query("SELECT l FROM Location l WHERE l.id = :locationId")
    Optional<Location> findLocationById(@Param("locationId") Long locationId);
}
