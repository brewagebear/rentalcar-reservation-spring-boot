package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.BorrowedDate;
import com.toyota.rentalcar.dev.domain.PersonalInfo;
import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class BorrowedDateSaveRequestDto {

    private Calendar startDate;
    private Calendar endDate;
    private RentalCar car;
    private PersonalInfo personalInfo;

    public BorrowedDate toEntity(){
        return BorrowedDate.builder()
                .startDate(startDate)
                .endDate(endDate)
                .car(car)
                .build();
    }
}
