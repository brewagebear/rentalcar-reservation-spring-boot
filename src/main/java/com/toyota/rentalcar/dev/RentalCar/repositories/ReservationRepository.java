package com.toyota.rentalcar.dev.RentalCar.repositories;

import com.toyota.rentalcar.dev.RentalCar.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
