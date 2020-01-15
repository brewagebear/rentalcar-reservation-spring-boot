package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.InvalidDate;
import com.toyota.rentalcar.dev.domain.RentalCar;
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
