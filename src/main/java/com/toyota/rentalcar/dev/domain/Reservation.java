package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.UUID;

@Entity
@Getter
@Table(name = "tbl_reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID reservationId = UUID.randomUUID();

    @Embedded
    @Valid
    private ReservationDates dates = new ReservationDates();

    @Builder Reservation(ReservationDates dates){
        this.dates = dates;
    }
}
