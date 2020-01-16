package com.toyota.rentalcar.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarReservationId implements Serializable {

    @EqualsAndHashCode.Include
    private Long rentalCar;

    @EqualsAndHashCode.Include
    private Long reservation;

    public CarReservationId(Long rentalCar, Long reservation) {
        rentalCar = rentalCar;
        reservation = reservation;
    }
}
