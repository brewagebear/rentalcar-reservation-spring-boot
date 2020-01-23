package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.InvalidDate;
import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RentalCarUpdateRequestDto {

    private RentalLocation rentalLocation;
    private String carModelName;
    private String  carImgURL;
    private BigDecimal costPerNight;
    private BigDecimal costDeposit;
    private BigDecimal costPerNightWithGas;
    private String extraEx;
    private List<InvalidDate> invalidDates = new ArrayList<>();

    @Builder
    public RentalCarUpdateRequestDto(RentalLocation rentalLocation,
                                     String carModelName,
                                     String carImgURL,
                                     String extraEx,
                                     BigDecimal costPerNight,
                                     BigDecimal costDeposit,
                                     BigDecimal costPerNightWithGas){
        this.rentalLocation = rentalLocation;
        this.carModelName   = carModelName;
        this.carImgURL      = carImgURL;
        this.costPerNight   = costPerNight;
        this.costDeposit    = costDeposit;
        this.extraEx        = extraEx;
        this.costPerNightWithGas = costPerNightWithGas;
    }
}
