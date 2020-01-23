package com.toyota.rentalcar.dev.RentalCar.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Getter
@Table(name = "TBL_INVALID_DATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvalidDate {
    private static final long serialVersionUID = -1713505055304186211L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVALID_DATE_ID")
    private Long id;

    private Calendar startDate;
    private Calendar endDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rentalcar_id")
    private RentalCar car;

    @Builder
    public InvalidDate(Calendar startDate, Calendar endDate, RentalCar car){
        this.startDate = startDate;
        this.endDate   = endDate;
        this.car       = car;
    }
}
