package com.toyota.rentalcar.dev.RentalCar.repositories;


import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import com.toyota.rentalcar.dev.RentalCar.model.Station;
import com.toyota.rentalcar.dev.RentalCar.model.StationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByRentalLocation(RentalLocation rentalLocation);
    Optional<List<Station>> findAllByRentalLocationAndStationType(RentalLocation rentalLocation, StationType stationType);
}
