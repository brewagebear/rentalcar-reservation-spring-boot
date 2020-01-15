package com.toyota.rentalcar.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String name;
    private String phoneNumber;
    private String hotel;
    private String pickupPlace;
    private String returnPlace;
    private String airLine;
    private LocalDateTime arrivalTime;

    @Lob
    private String requirement;

    @Builder
    public PersonalInfo(String name,
                        String phoneNumber,
                        String hotel,
                        String pickupPlace,
                        String returnPlace,
                        String airLine,
                        LocalDateTime arrivalTime){

        this.name        = name;
        this.phoneNumber = phoneNumber;
        this.hotel       = hotel;
        this.pickupPlace = pickupPlace;
        this.returnPlace = returnPlace;
        this.airLine     = airLine;
        this.arrivalTime = arrivalTime;
    }
}
