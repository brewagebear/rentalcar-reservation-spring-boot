package com.toyota.rentalcar.dev.RentalCar.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toyota.rentalcar.dev.commons.model.BaseTimeEntity;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@Table(name = "tbl_rentalcar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalCar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalcar_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentalLocation rentalLocation;
    private String  carModelName;

    @Lob
    private String extraEx;
    private String  carImgURL;
    private BigDecimal costDeposit;
    private BigDecimal costPerNight;
    private BigDecimal costPerNightWithGas;

    @JsonIgnore
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvalidDate> invalidDates = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "rentalCar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CarReservation> carReservations = new ArrayList<>();

    @Builder
    public RentalCar(RentalLocation rentalLocation,
                     String carModelName,
                     String extraEx,
                     String carImgURL,
                     BigDecimal costDeposit,
                     BigDecimal costPerNight,
                     BigDecimal costPerNightWithGas) {
        this.rentalLocation      = rentalLocation;
        this.carModelName        = carModelName;
        this.extraEx             = extraEx;
        this.carImgURL           = carImgURL;
        this.costDeposit         = costDeposit;
        this.costPerNight        = costPerNight;
        this.costPerNightWithGas = costPerNightWithGas;
    }

    @Builder
    public void update(RentalCarUpdateRequestDto updateEntity){
        this.rentalLocation = updateEntity.getRentalLocation();
        this.carModelName   = updateEntity.getCarModelName();
        this.carImgURL      = updateEntity.getCarImgURL();
        this.extraEx        = updateEntity.getExtraEx();
        this.costPerNight   = updateEntity.getCostPerNight();
        this.costDeposit    = updateEntity.getCostDeposit();
        this.costPerNightWithGas = updateEntity.getCostPerNightWithGas();
    }

    public void update(InvalidDate invalidDate) {
        this.addInvalidDate(invalidDate);
    }

    public void addInvalidDate(InvalidDate invalidDate) { this.invalidDates.add(invalidDate); }
    public void addReservation(CarReservation carReservation) {
        this.carReservations.add(carReservation);
    }

}
