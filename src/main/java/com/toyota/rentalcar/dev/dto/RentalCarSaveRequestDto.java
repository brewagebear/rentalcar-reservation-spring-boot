package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.RentalCar;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RentalCarSaveRequestDto {

    private RentalLocation rentalLocation;
    private String  carModelName;
    private String extraEx;
    private String  carImgURL;
    private BigDecimal costDeposit;
    private BigDecimal costPerNight;
    private BigDecimal costPerNightWithGas;

    @Builder
    public RentalCarSaveRequestDto(RentalLocation rentalLocation,
                                   String carModelName,
                                   String carImgURL,
                                   String extraEx,
                                   BigDecimal costDeposit,
                                   BigDecimal costPerNight,
                                   BigDecimal costPerNightWithGas){
        this.rentalLocation = rentalLocation;
        this.carModelName   = carModelName;
        this.carImgURL      = carImgURL;
        this.extraEx        = extraEx;
        this.costDeposit    = costDeposit;
        this.costPerNight   = costPerNight;
        this.costPerNightWithGas = costPerNightWithGas;
    }

    public RentalCar toEntity(){
        return RentalCar.builder()
                .rentalLocation(rentalLocation)
                .carModelName(carModelName)
                .carImgURL(carImgURL)
                .extraEx(extraEx)
                .costDeposit(costDeposit)
                .costPerNight(costPerNight)
                .costPerNightWithGas(costPerNightWithGas)
                .build();
    }
}
