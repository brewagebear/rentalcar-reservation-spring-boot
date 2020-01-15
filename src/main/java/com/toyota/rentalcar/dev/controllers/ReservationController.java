package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.domain.Reservation;
import com.toyota.rentalcar.dev.domain.flow.ReservationFlow;
import com.toyota.rentalcar.dev.dto.BorrowedDateSaveRequestDto;
import com.toyota.rentalcar.dev.services.DateService;
import com.toyota.rentalcar.dev.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@SessionAttributes("reservationFlow")
public class ReservationController {

    private RentalCarService carService;
    private DateService      dateService;

    @ModelAttribute("reservationFlow")
    public ReservationFlow getReservationFlow() { return new ReservationFlow();}

    @PostMapping("/reservation/{id}")
    public String date(@PathVariable Long id,
                       @ModelAttribute("reservationFlow") ReservationFlow reservationFlow,
                       @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar startDate,
                       @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar endDate,
                       RedirectAttributes redirectAttributes){

        reservationFlow.enterStep(ReservationFlow.Step.Dates);

        RentalCar car = carService.findOne(id);
        BorrowedDateSaveRequestDto target = new BorrowedDateSaveRequestDto();

        target.setCar(car);
        target.setStartDate(startDate);
        target.setEndDate(endDate);

        reservationFlow.completeStep(ReservationFlow.Step.Dates);
        redirectAttributes.addFlashAttribute("reservationFlow", reservationFlow);

    }
}
