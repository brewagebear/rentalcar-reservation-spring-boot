package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tbl_car_reservation")
@IdClass(CarReservationId.class)
@NoArgsConstructor
public class CarReservation {

    @Id
    @ManyToOne
    @JoinColumn(name = "rentalcar_id", referencedColumnName = "rentalcar_id")
    private RentalCar rentalCar;

    @Id
    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    @Builder
    public CarReservation(RentalCar rentalCar, Reservation reservation){
        this.rentalCar = rentalCar;
        this.reservation = reservation;
    }

}
