package com.toyota.rentalcar.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerPickupStationId implements Serializable {

    @EqualsAndHashCode.Include
    private Long customer;
    @EqualsAndHashCode.Include
    private Long pickupStation;

    public CustomerPickupStationId(Long customer, Long pickupStation){
        this.customer      = customer;
        this.pickupStation = pickupStation;
    }
}
