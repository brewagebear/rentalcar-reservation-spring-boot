package com.toyota.rentalcar.dev.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_rentalcar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalcar_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentalCarType rentalCarType;
    private String  carModelName;
    private String  carImgSource;
    private BigDecimal costPerNight;

    @OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL)
    private List<RentalCarTag> rentalCarTags = new ArrayList<>();

    @Builder
    public RentalCar(RentalCarType rentalCarType, String carModelName, String carImgSource, BigDecimal costPerNight) {
        this.rentalCarType = rentalCarType;
        this.carModelName = carModelName;
        this.carImgSource = carImgSource;
        this.costPerNight = costPerNight;
    }
}
