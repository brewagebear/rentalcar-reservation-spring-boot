package com.toyota.rentalcar.dev.RentalCar.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TBL_ACCOMMODATION")
@EqualsAndHashCode(exclude = {"id", "value"})
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentalLocation rentalLocation;

    private String name;
    private String value;

    @Builder
    public Accommodation(Long id, RentalLocation rentalLocation, String name, String value){
        this.id             = id;
        this.rentalLocation = rentalLocation;
        this.name  = name;
        this.value = value;
    }

    public void update(RentalLocation rentalLocation, String name, String value){
        this.rentalLocation = rentalLocation;
        this.name     = name;
        this.value    = value;
    }
}
