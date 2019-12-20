package com.toyota.rentalcar.dev.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "tbl_rentalcar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarType carType;
    private String  carModelName;
    private String  carImgSource;
    private BigDecimal costPerNight;

    @Builder
    public RentalCar(CarType carType, String carModelName, String carImgSource, BigDecimal costPerNight) {
        this.carType      = carType;
        this.carModelName = carModelName;
        this.carImgSource = carImgSource;
        this.costPerNight = costPerNight;
    }
}
