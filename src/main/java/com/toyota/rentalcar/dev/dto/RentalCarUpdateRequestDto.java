package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.ReservationDates;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RentalCarUpdateRequestDto {

    private String carModelName;
    private String carImgSource;
    private BigDecimal costPerNight;
    private ReservationDates date;

    @Builder
    public RentalCarUpdateRequestDto(String carModelName, String carImgSource, BigDecimal costPerNight, ReservationDates date){
        this.carModelName = carModelName;
        this.carImgSource = carImgSource;
        this.costPerNight = costPerNight;
        this.date         = date;
    }
}
