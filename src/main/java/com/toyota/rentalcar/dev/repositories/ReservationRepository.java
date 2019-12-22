package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
