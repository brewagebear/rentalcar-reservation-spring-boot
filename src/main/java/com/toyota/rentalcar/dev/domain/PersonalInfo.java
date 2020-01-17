package com.toyota.rentalcar.dev.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_personalInfo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_info_id")
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String hotel;
    private String pickupPlace;
    private String returnPlace;
    private String airLine;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    @Lob
    private String requirement;

    @Builder
    public PersonalInfo(String name,
                        String phoneNumber,
                        String email,
                        String hotel,
                        String pickupPlace,
                        String returnPlace,
                        String airLine,
                        LocalDateTime arrivalTime,
                        String requirement){

        this.name        = name;
        this.phoneNumber = phoneNumber;
        this.email       = email;
        this.hotel       = hotel;
        this.pickupPlace = pickupPlace;
        this.returnPlace = returnPlace;
        this.airLine     = airLine;
        this.arrivalTime = arrivalTime;
        this.requirement = requirement;
    }
}
