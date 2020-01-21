package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CarReservationResponseDto {


    private UUID reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private ReservationStatus status;
    private RentalLocation local;
    private String carModelName;
    private BigDecimal totalCost;
    private BigDecimal firstCost;
    private BigDecimal lastCost;
    private Boolean    isGas;
    private Boolean    isIceBox;
    private Boolean    isAirportMeeting;
    private int        numOfChild;
    private String     name;
    private String     phoneNum;
    private String     email;
    private String     hotel;
    private String     pickupPlace;
    private String     returnPlace;
    private String     airLine;
    private LocalDateTime arrivalTime;
    private String     requirement;

    @Builder
    public CarReservationResponseDto(CarReservation entity) {
       this.reservationId = entity.getReservation().getId();
       this.startDate     = entity.getReservation().getDates().getStartDate();
       this.endDate       = entity.getReservation().getDates().getEndDate();
       this.startTime     = entity.getReservation().getDates().getStartTime();
       this.endTime       = entity.getReservation().getDates().getEndTime();
       this.status        = entity.getReservation().getStatus();
       this.local         = entity.getRentalCar().getRentalLocation();
       this.carModelName  = entity.getRentalCar().getCarModelName();
       this.totalCost     = entity.getReservation().getTotalCost();
       this.firstCost     = entity.getReservation().getFirstCost();
       this.lastCost      = entity.getReservation().getLastCost();
       this.isGas         = entity.getReservation().isIGas();
       this.isIceBox      = entity.getReservation().isIIceBox();
       this.isAirportMeeting = entity.getReservation().isIAirportMeeting();
       this.numOfChild    = entity.getReservation().getNumOfChild();
       this.name          = entity.getReservation().getPersonalInfo().getName();
       this.phoneNum      = entity.getReservation().getPersonalInfo().getPhoneNumber();
       this.email         = entity.getReservation().getPersonalInfo().getEmail();
//       this.hotel         = entity.getReservation().getPersonalInfo().getHotel();
//       this.pickupPlace   = entity.getReservation().getPersonalInfo().getPickupPlace();
//       this.returnPlace   = entity.getReservation().getPersonalInfo().getReturnPlace();
       this.airLine       = entity.getReservation().getPersonalInfo().getAirLine();
       this.arrivalTime   = entity.getReservation().getPersonalInfo().getArrivalTime();
       this.requirement   = entity.getReservation().getPersonalInfo().getRequirement();
    }
}
