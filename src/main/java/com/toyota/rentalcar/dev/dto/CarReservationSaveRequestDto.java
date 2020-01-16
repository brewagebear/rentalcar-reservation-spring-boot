package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.CarReservation;
import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarReservationSaveRequestDto {

    private RentalCar rentalCar;
    private Reservation reservation;

    public CarReservation toEntity(){
        return CarReservation.builder()
                .rentalCar(rentalCar)
                .reservation(reservation)
                .build();
    }
}
