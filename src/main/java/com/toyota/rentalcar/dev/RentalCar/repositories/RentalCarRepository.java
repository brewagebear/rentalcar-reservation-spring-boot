package com.toyota.rentalcar.dev.RentalCar.repositories;

import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalCarRepository extends JpaRepository<RentalCar, Long> {
}
