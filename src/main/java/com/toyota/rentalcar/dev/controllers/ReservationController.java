package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.CarReservation;
import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.domain.ReservationDates;
import com.toyota.rentalcar.dev.domain.ReservationStatus;
import com.toyota.rentalcar.dev.dto.CarReservationResponseDto;
import com.toyota.rentalcar.dev.dto.CarReservationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.PersonalInfoSaveRequestDto;
import com.toyota.rentalcar.dev.dto.ReservationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.services.RentalCarService;
import com.toyota.rentalcar.dev.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private  final RentalCarService carService;
    private  final ReservationService reservationService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @GetMapping("/reservation")
    public List<CarReservationResponseDto> printReservation(){
        return reservationService.findAll();
    }

    @PutMapping("/reservation/{reservationId}")
    public ResponseEntity<?> changeStatus(@PathVariable UUID reservationId,
                                          @RequestParam(value= "status") ReservationStatus status){
        CarReservationResponseDto result = reservationService.updateConfirmStatus(reservationId, status);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/reservation/{reservationId}")
                .buildAndExpand(result.getReservationId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "예약 상태가 성공적으로 변경되었습니다."));
    }

    @PostMapping("/reservation/car/{id}")
    public ResponseEntity<?> saveReservation(@PathVariable Long id,
                               @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                               @RequestParam(value = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                               @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                               @RequestBody ReservationSaveRequestDto  reservationSaveRequestDto){

        CarReservationSaveRequestDto carReservationDto = new CarReservationSaveRequestDto();
        ReservationSaveRequestDto    reservationDto    = new ReservationSaveRequestDto();
        PersonalInfoSaveRequestDto   personalDto       = new PersonalInfoSaveRequestDto(reservationSaveRequestDto.getPersonalInfo().getName(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getPhoneNumber(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getEmail(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getHotel(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getPickupPlace(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getReturnPlace(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getAirLine(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getArrivalTime(),
                                                                                        reservationSaveRequestDto.getPersonalInfo().getRequirement());
        RentalCar maybeCar = carService.findOne(id);
        ReservationDates dates = ReservationDates.builder()
                .startDate(startDate)
                .startTime(startTime)
                .endDate(endDate)
                .endTime(startTime)
                .build();

        reservationDto.setDates(dates);
        reservationDto.setStatus(reservationSaveRequestDto.getStatus());
        reservationDto.setLocal(maybeCar.getRentalLocation());
        reservationDto.setTotalCost(reservationSaveRequestDto.getTotalCost());
        reservationDto.setFirstCost(reservationSaveRequestDto.getFirstCost());
        reservationDto.setLastCost(reservationSaveRequestDto.getLastCost());
        reservationDto.setIGas(reservationSaveRequestDto.isIGas());
        reservationDto.setIIceBox(reservationSaveRequestDto.isIIceBox());
        reservationDto.setIAirportMeeting(reservationSaveRequestDto.isIAirportMeeting());
        reservationDto.setNumOfChild(reservationSaveRequestDto.getNumOfChild());

        carReservationDto.setRentalCar(maybeCar);
        CarReservation result = reservationService.completeReservation(personalDto, reservationDto, carReservationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/reservation/{reservationId}")
                .buildAndExpand(result.getReservation().getId()).toUri();

       return ResponseEntity.created(location).body(new ApiResponse(true, "예약이 성공적으로 등록되었습니다."));
    }

}
