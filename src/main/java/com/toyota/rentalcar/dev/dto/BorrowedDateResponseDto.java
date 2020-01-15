package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.PersonalInfo;
import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Getter;

import java.util.Calendar;

@Getter
public class BorrowedDateResponseDto {

    private Calendar startDate;
    private Calendar endDate;
    private RentalCar car;
    private PersonalInfo personalInfo;

    public BorrowedDateResponseDto(Calendar startDate, Calendar endDate, RentalCar car, PersonalInfo personalInfo) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
        this.personalInfo = personalInfo;
    }
}
