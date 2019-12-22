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
@Table(name = "tbl_customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String passportName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerPickupStation> customerPickupStations = new ArrayList<>();

    @Builder
    public Customer(String passportName){
        this.passportName = passportName;
    }
}
