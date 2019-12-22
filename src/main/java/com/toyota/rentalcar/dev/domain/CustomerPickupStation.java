package com.toyota.rentalcar.dev.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_customer_pickup")
@IdClass(CustomerPickupStationId.class)
@NoArgsConstructor
public class CustomerPickupStation {

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "pickupstation_id", referencedColumnName = "pickupstation_id")
    private PickupStation pickupStation;

    @Builder
    public CustomerPickupStation(Customer customer, PickupStation pickupStation){
        this.customer      = customer;
        this.pickupStation = pickupStation;
    }
}
