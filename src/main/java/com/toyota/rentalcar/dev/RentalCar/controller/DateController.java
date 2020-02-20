package com.toyota.rentalcar.dev.RentalCar.controller;

import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import com.toyota.rentalcar.dev.RentalCar.dto.InvalidDateSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.services.DateService;
import com.toyota.rentalcar.dev.RentalCar.services.RentalCarService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "예약 가능한 날짜 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start_date", value = "예약 조회 시작날짜", required = true, dataType = "date"),
            @ApiImplicitParam(name = "end_date", value = "예약 조회 끝날짜", required = true, dataType = "date")
    })
    @GetMapping(value = "/available-date")
    public Map<String, Object> getAvailableDate(
            @RequestParam(value = "startDate", defaultValue = "1800-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar startDate,
            @RequestParam(value = "endDate", defaultValue = "3000-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Calendar endDate){

        Map<String, Object> map = new HashMap<>();
        List<RentalCar> list = dateService.checkAvailableCars(startDate, endDate);
        map.put("RentalCar", list);
        return map;
    }

    @ApiOperation(value = "예약 불가능한 날짜 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start_date", value = "예약 불가능 시작날짜", required = true, dataType = "date"),
            @ApiImplicitParam(name = "end_date", value = "예약 불가능 끝날짜", required = true, dataType = "date")
    })
    @PostMapping(value = "/invalid-date/{car_id}")
    public Long saveInvalidDate(@PathVariable("car_id")Long carId,
                     @RequestParam(value = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar startDate,
                     @RequestParam(value = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar endDate){

        RentalCar car = carService.findOne(carId);
        InvalidDateSaveRequestDto target = new InvalidDateSaveRequestDto();

        target.setCar(car);
        target.setStartDate(startDate);
        target.setEndDate(endDate);

        car.update(target.toEntity());

        return dateService.saveInvalidDate(target);
    }
}
