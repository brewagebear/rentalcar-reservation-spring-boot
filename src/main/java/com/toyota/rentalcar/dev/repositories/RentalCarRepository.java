package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalCarRepository extends JpaRepository<RentalCar, Long> {
}
