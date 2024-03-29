package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
