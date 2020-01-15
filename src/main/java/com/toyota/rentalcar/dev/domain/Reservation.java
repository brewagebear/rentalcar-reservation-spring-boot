package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

/***
 * 예약 옵션 분할해야함.
 */
@Entity
@Getter
@Table(name = "tbl_reservation")
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID reservationId = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private RentalLocation local;

    @ManyToOne
    private RentalCar rentalCar;

    @OneToOne
    private PersonalInfo personalInfo;

    private BigDecimal firstCost;
    private BigDecimal lastCost;
    private BigDecimal totalCost;

    private boolean iGas;
    private boolean iIceBox;
    private boolean iAirportMeeting;
    private int     numOfChild;

    @Builder
    public Reservation(ReservationStatus status, RentalLocation local, RentalCar rentalCar, PersonalInfo personalInfo, BigDecimal firstCost, BigDecimal lastCost, BigDecimal totalCost, boolean iGas, boolean iIceBox, boolean iAirportMeeting, int numOfChild) {
        this.status          = status;
        this.local           = local;
        this.rentalCar       = rentalCar;
        this.personalInfo    = personalInfo;
        this.firstCost       = firstCost;
        this.lastCost        = lastCost;
        this.totalCost       = totalCost;
        this.iGas            = iGas;
        this.iIceBox         = iIceBox;
        this.iAirportMeeting = iAirportMeeting;
        this.numOfChild      = numOfChild;
    }
}
