package com.toyota.rentalcar.dev.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "reservation_id")
    private Long id;

    private UUID reservationRegNum = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private RentalLocation local;

    @Embedded
    @Valid
    private ReservationDates dates = new ReservationDates();

    @OneToOne
    private PersonalInfo personalInfo;

    private BigDecimal firstCost;
    private BigDecimal lastCost;
    private BigDecimal totalCost;

    private boolean iGas;
    private boolean iIceBox;
    private boolean iAirportMeeting;
    private int     numOfChild;

    @OneToMany(mappedBy = "reservation")
    private List<CarReservation> carReservations = new ArrayList<>();

    @Builder
    public Reservation(ReservationStatus status,
                       RentalLocation local,
                       ReservationDates dates,
                       PersonalInfo personalInfo,
                       BigDecimal firstCost,
                       BigDecimal lastCost,
                       BigDecimal totalCost,
                       boolean iGas,
                       boolean iIceBox,
                       boolean iAirportMeeting,
                       int numOfChild) {
        this.status          = status;
        this.local           = local;
        this.dates           = dates;
        this.personalInfo    = personalInfo;
        this.firstCost       = firstCost;
        this.lastCost        = lastCost;
        this.totalCost       = totalCost;
        this.iGas            = iGas;
        this.iIceBox         = iIceBox;
        this.iAirportMeeting = iAirportMeeting;
        this.numOfChild      = numOfChild;
    }

    public void setDates(ReservationDates dates) {
        this.dates = dates;
    }
}
