package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.CarReservation;
import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import com.toyota.rentalcar.dev.RentalCar.model.Reservation;
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
