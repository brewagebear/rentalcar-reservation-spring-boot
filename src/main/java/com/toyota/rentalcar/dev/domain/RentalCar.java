package com.toyota.rentalcar.dev.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private List<BorrowedDate> borrowedDates = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private List<InvalidDate> invalidDates = new ArrayList<>();

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

    public void update(String carModelName, String carImgURL, BigDecimal costPerNight){
        this.carModelName = carModelName;
        this.carImgURL = carImgURL;
        this.costPerNight = costPerNight;
    }

    public void update(BorrowedDate borrowedDate) {
        this.addBorrowedDate(borrowedDate);
    }
    public void update(InvalidDate invalidDate) {
        this.addInvalidDate(invalidDate);
    }

    public void addBorrowedDate(BorrowedDate borrowedDate){
        this.borrowedDates.add(borrowedDate);
    }
    public void addInvalidDate(InvalidDate invalidDate) { this.invalidDates.add(invalidDate); }

}
