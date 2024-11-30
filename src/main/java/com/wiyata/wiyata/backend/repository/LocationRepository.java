package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.repository.customQuery.LocationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

    @Query("SELECT l FROM Location l WHERE l.isMember = false")
    List<Location> findAllByIsMemberFalse();
}
