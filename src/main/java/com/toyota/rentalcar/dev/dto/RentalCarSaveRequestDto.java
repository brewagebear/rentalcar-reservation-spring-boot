package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalCarType;
import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Builder;
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

    @Builder
    public RentalCarSaveRequestDto(RentalCarType carType,
                                   String carModelName,
                                   String carImgSource,
                                   BigDecimal costPerNight){
        this.rentalCarType = carType;
        this.carModelName  = carModelName;
        this.carImgSource  = carImgSource;
        this.costPerNight  = costPerNight;
    }

    public RentalCar toEntity(){
        return RentalCar.builder()
                .rentalCarType(rentalCarType)
                .carModelName(carModelName)
                .carImgSource(carImgSource)
                .costPerNight(costPerNight)
                .build();
    }
}
