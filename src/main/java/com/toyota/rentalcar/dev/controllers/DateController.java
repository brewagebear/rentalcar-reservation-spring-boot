package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.dto.InvalidDateSaveRequestDto;
import com.toyota.rentalcar.dev.services.DateService;
import com.toyota.rentalcar.dev.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/date")
public class DateController {

    private final DateService dateService;
    private final RentalCarService carService;

    // 클린코드 예정
    @GetMapping(value = "/available-date")
    public Map<String, Object> getAvailableDate(
            @RequestParam(value = "startDate", defaultValue = "1800-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar startDate,
            @RequestParam(value = "endDate", defaultValue = "3000-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Calendar endDate){

        Map<String, Object> map = new HashMap<>();
        List<RentalCar> list = dateService.checkAvailableCars(startDate, endDate);
        map.put("RentalCar", list);
        return map;
    }

    @PostMapping(value = "/invalid-date/{id}")
    public Long saveInvalidDate(@PathVariable Long id,
                     @RequestParam(value = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar startDate,
                     @RequestParam(value = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar endDate){

        RentalCar car = carService.findOne(id);
        InvalidDateSaveRequestDto target = new InvalidDateSaveRequestDto();

        target.setCar(car);
        target.setStartDate(startDate);
        target.setEndDate(endDate);

        car.update(target.toEntity());

        return dateService.saveInvalidDate(target);
    }
}
