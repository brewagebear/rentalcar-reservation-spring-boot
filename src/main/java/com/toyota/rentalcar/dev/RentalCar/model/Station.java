package com.toyota.rentalcar.dev.RentalCar.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TBL_STATION")
@EqualsAndHashCode(exclude = {"id", "value"})
public class Station {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentalLocation rentalLocation;

    @Enumerated(EnumType.STRING)
    private StationType stationType;

    private String name;
    private String value;

    @Builder
    public Station(Long id,
                   RentalLocation location,
                   StationType stationType,
                   String name,
                   String value){
        this.id             = id;
        this.rentalLocation = location;
        this.stationType    = stationType;
        this.name           = name;
        this.value          = value;
    }

    public void update(RentalLocation location,
                       StationType stationType,
                       String name,
                       String value){
        this.rentalLocation = location;
        this.stationType    = stationType;
        this.name           = name;
        this.value          = value;
    }
}
