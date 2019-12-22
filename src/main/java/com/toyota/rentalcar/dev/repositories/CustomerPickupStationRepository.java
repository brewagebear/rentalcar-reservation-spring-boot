package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.CustomerPickupStation;
import com.toyota.rentalcar.dev.domain.CustomerPickupStationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPickupStationRepository extends JpaRepository <CustomerPickupStation, CustomerPickupStationId> {
}
