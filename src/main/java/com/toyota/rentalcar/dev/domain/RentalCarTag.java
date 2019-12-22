package com.toyota.rentalcar.dev.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_rentalcar_tag")
@IdClass(RentalCarTagId.class)
@NoArgsConstructor
public class RentalCarTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "rentalcar_id", referencedColumnName = "rentalcar_id")
    private RentalCar rentalCar;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")
    private Tag tag;

    @Builder
    public RentalCarTag(RentalCar car, Tag tag){
        this.rentalCar = car;
        this.tag       = tag;
    }
}
