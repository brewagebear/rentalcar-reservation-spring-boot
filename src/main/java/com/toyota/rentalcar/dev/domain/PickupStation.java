package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PickupStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pickupstation_id")
    private Long id;
    private String title;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pickupStation")
    private List<CustomerPickupStation> customerPickupStations = new ArrayList<>();

    @Builder
    public PickupStation(String title){
        this.title = title;
    }
}
