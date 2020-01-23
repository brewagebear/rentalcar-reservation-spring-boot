package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReservationResponseDto {
    private UUID id;
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

    @Builder
    public ReservationResponseDto(Reservation entity) {
        this.id              = entity.getId();
        this.status          = entity.getStatus();
        this.local           = entity.getLocal();
        this.dates           = entity.getDates();
        this.personalInfo    = entity.getPersonalInfo();
        this.firstCost       = entity.getFirstCost();
        this.lastCost        = entity.getLastCost();
        this.totalCost       = entity.getTotalCost();
        this.iGas            = entity.isIGas();
        this.iIceBox         = entity.isIIceBox();
        this.iAirportMeeting = entity.isIAirportMeeting();
        this.numOfChild      = entity.getNumOfChild();
    }

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
