package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RentalCarResponseDto {

    private Long id;
    private RentalLocation rentalLocation;
    private String  carModelName;
    private String extraEx;
    private String  carImgURL;
    private BigDecimal costDeposit;
    private BigDecimal costPerNight;
    private BigDecimal costPerNightWithGas;

    @Builder
    public RentalCarResponseDto(RentalCar entity){
        this.id             = entity.getId();
        this.rentalLocation = entity.getRentalLocation();
        this.carModelName   = entity.getCarModelName();
        this.extraEx        = entity.getExtraEx();
        this.carImgURL      = entity.getCarImgURL();
        this.costDeposit    = entity.getCostDeposit();
        this.costPerNight   = entity.getCostPerNight();
        this.costPerNightWithGas = entity.getCostPerNightWithGas();
    }

    public RentalCar toEntity(){
        return RentalCar.builder()
                .rentalLocation(rentalLocation)
                .carModelName(carModelName)
                .extraEx(extraEx)
                .carImgURL(carImgURL)
                .costDeposit(costDeposit)
                .costPerNight(costPerNight)
                .costPerNightWithGas(costPerNightWithGas)
                .build();
    }
}
