package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.PickupStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupStationRepository extends JpaRepository<PickupStation, Long> {
}
