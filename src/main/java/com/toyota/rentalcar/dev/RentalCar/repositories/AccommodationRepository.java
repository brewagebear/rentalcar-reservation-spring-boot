package com.toyota.rentalcar.dev.RentalCar.repositories;

import com.toyota.rentalcar.dev.RentalCar.model.Accommodation;
import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository <Accommodation, Long> {
    List<Accommodation> findAccommodationsByRentalLocation(RentalLocation rentalLocation);
    Optional<List<Accommodation>> findAllByRentalLocation(RentalLocation rentalLocation);
}
