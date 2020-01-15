package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Getter
@Entity
@Table(name = "TBL_BORROWED_DATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowedDate implements Serializable {
    private static final long serialVersionUID = -1713505055304086201L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRROWED_DATE_ID")
    private Long id;

    private Calendar startDate;
    private Calendar endDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rentalcar_id")
    private RentalCar car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private PersonalInfo personalInfo;

    @Builder
    public BorrowedDate(Calendar startDate, Calendar endDate, RentalCar car, PersonalInfo personalInfo){
        this.startDate = startDate;
        this.endDate   = endDate;
        this.car       = car;
        this.personalInfo = personalInfo;
    }
}
