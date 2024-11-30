package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.repository.customQuery.LocationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

}
