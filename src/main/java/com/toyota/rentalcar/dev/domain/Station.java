package com.toyota.rentalcar.dev.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TBL_STATION")
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
    public Station(RentalLocation location,
                   StationType stationType,
                   String name,
                   String value){
        this.rentalLocation = location;
        this.stationType    = stationType;
        this.name           = name;
        this.value          = value;
    }
}
