package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.InvalidDate;
import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class InvalidDateSaveRequestDto {

    private Calendar startDate;
    private Calendar endDate;
    private RentalCar car;

    public InvalidDate toEntity(){
        return InvalidDate.builder()
                .startDate(startDate)
                .endDate(endDate)
                .car(car)
                .build();
    }
}
