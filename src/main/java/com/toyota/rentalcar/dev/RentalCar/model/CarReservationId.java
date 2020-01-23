package com.toyota.rentalcar.dev.RentalCar.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarReservationId implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long rentalCar;

    @Type(type = "uuid-char")
    @EqualsAndHashCode.Include
    private UUID reservation;

    public CarReservationId(Long rentalCar, UUID reservation) {
        this.rentalCar = rentalCar;
        this.reservation = reservation;
    }
}
