package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalCarType;
import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RentalCarSaveRequestDto {

    private RentalCarType rentalCarType;
    private String  carModelName;
    private String  carImgSource;
    private BigDecimal costPerNight;

    public RentalCar toEntity(){
        return RentalCar.builder()
                .rentalCarType(rentalCarType)
                .carModelName(carModelName)
                .carImgSource(carImgSource)
                .costPerNight(costPerNight)
                .build();
    }
}
