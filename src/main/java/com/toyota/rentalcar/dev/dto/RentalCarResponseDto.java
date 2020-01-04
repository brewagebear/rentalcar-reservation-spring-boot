package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RentalCarResponseDto {

    private Long id;
    private String  carModelName;
    private String  carImgSource;
    private BigDecimal costPerNight;

    public RentalCarResponseDto(RentalCar entity){
        this.id            = entity.getId();
        this.carModelName  = entity.getCarModelName();
        this.carImgSource  = entity.getCarImgSource();
        this.costPerNight  = entity.getCostPerNight();
    }

}
