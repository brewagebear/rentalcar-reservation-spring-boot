package com.toyota.rentalcar.dev.repositories;


import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.Station;
import com.toyota.rentalcar.dev.domain.StationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByRentalLocation(RentalLocation rentalLocation);
    List<Station> findAllByRentalLocationAndStationType(RentalLocation rentalLocation, StationType stationType);
}
