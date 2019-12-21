package com.toyota.rentalcar.dev.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RentalCarTagId implements Serializable {

    @EqualsAndHashCode.Include
    private Long rentalCar;
    @EqualsAndHashCode.Include
    private Long tag;

    public RentalCarTagId(Long rentalCar, Long tag) {
        this.rentalCar = rentalCar;
        this.tag = tag;
    }
}
