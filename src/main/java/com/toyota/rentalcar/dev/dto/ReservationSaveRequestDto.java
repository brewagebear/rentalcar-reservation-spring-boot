package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ReservationSaveRequestDto {

    private ReservationStatus status;
    private RentalLocation local;
    private ReservationDates dates;
    private PersonalInfo personalInfo;
    private BigDecimal firstCost;
    private BigDecimal lastCost;
    private BigDecimal totalCost;

    private boolean iGas;
    private boolean iIceBox;
    private boolean iAirportMeeting;
    private int     numOfChild;

    public Reservation toEntity(){
        return Reservation.builder()
                .status(status)
                .local(local)
                .dates(dates)
                .personalInfo(personalInfo)
                .firstCost(firstCost)
                .lastCost(lastCost)
                .totalCost(totalCost)
                .iGas(iGas)
                .iAirportMeeting(iAirportMeeting)
                .numOfChild(numOfChild)
                .build();
    }
}
