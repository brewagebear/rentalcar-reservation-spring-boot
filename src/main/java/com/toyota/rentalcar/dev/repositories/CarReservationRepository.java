package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.CarReservation;
import com.toyota.rentalcar.dev.domain.CarReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReservationRepository extends JpaRepository<CarReservation, CarReservationId> {
}
