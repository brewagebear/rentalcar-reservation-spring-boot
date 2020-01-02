package com.toyota.rentalcar.dev.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RentalCarUpdateRequestDto {

    private String carModelName;
    private String carImgSource;
    private BigDecimal costPerNight;

    @Builder
    public RentalCarUpdateRequestDto(String carModelName, String carImgSource, BigDecimal costPerNight){
        this.carModelName = carModelName;
        this.carImgSource = carImgSource;
        this.costPerNight = costPerNight;
    }

}
