package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.PersonalInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PersonalInfoSaveRequestDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String hotel;
    private String pickupPlace;
    private String returnPlace;
    private String airLine;
    private LocalDateTime arrivalTime;
    private String requirement;

    public PersonalInfoSaveRequestDto(String name, String phoneNumber, String email, String hotel, String pickupPlace, String returnPlace, String airLine, LocalDateTime arrivalTime, String requirement) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email       = email;
        this.hotel = hotel;
        this.pickupPlace = pickupPlace;
        this.returnPlace = returnPlace;
        this.airLine = airLine;
        this.arrivalTime = arrivalTime;
        this.requirement = requirement;
    }

    public PersonalInfo toEntity(){
        return PersonalInfo.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .hotel(hotel)
                .pickupPlace(pickupPlace)
                .returnPlace(returnPlace)
                .airLine(airLine)
                .arrivalTime(arrivalTime)
                .requirement(requirement)
                .build();
    }

}
