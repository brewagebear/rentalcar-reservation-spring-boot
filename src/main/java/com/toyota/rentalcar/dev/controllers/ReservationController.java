package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.Reservation;
import com.toyota.rentalcar.dev.domain.ReservationDates;
import com.toyota.rentalcar.dev.domain.TimeProvider;
import com.toyota.rentalcar.dev.domain.flow.ReservationFlow;
import com.toyota.rentalcar.dev.dto.CarReservationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.dto.ReservationSaveRequestDto;
import com.toyota.rentalcar.dev.services.DateService;
import com.toyota.rentalcar.dev.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SessionAttributes("reservationFlow")
public class ReservationController {

    private  final RentalCarService carService;
    private  final DateService      dateService;
    private  final TimeProvider timeProvider;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @ModelAttribute("reservationFlow")
    public ReservationFlow getReservationFlow() { return new ReservationFlow();}

    @PostMapping("/reservation/{id}")
    public Reservation date(@PathVariable Long id,
                       @ModelAttribute("reservationFlow") ReservationFlow reservationFlow,
                       @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                       BindingResult bindingResult){

        reservationFlow.enterStep(ReservationFlow.Step.Dates);

        CarReservationSaveRequestDto requestDto = new CarReservationSaveRequestDto();
        RentalCarResponseDto maybeCar = carService.findById(id);
        ReservationDates dates = ReservationDates.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        reservationFlow.getReservation().setDates(dates);

        requestDto.setReservation(reservationFlow.getReservation());
        requestDto.setRentalCar(maybeCar.toEntity());

        reservationFlow.setCarReservation(requestDto.toEntity());

        Optional<ReservationDates.ValidationError> validationError =
                reservationFlow.getReservation().getDates().vaildate(timeProvider.localDate());

        if(validationError.isPresent()){
            bindingResult.rejectValue("reservation.dates", validationError.get().getCode(), validationError.get().getReason());
        }

        reservationFlow.completeStep(ReservationFlow.Step.Dates);
        return reservationFlow.getReservation();
    }
}
