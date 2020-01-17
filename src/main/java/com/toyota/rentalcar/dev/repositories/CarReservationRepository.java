package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.CarReservation;
import com.toyota.rentalcar.dev.domain.CarReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CarReservationRepository extends JpaRepository<CarReservation, CarReservationId> {
    Optional<CarReservation> findAllByReservationId(@Param("reservation_id") UUID reservationId);
    Collection<CarReservation> findAllByRentalCarIdAndReservationId(@Param("rentalcar_id") Long rentalcarId, @Param("reservation_id") UUID reservationId);
}
