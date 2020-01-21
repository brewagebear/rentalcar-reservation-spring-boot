package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.PersonalInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PersonalInfoResponseDto {

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

    @Builder
    public PersonalInfoResponseDto(PersonalInfo entity) {
        this.id          = entity.getId();
        this.name        = entity.getName();
        this.phoneNumber = entity.getPhoneNumber();
        this.email       = entity.getEmail();
//        this.hotel       = entity.getHotel();
//        this.pickupPlace = entity.getPickupPlace();
//        this.returnPlace = entity.getReturnPlace();
        this.airLine     = entity.getAirLine();
        this.arrivalTime = entity.getArrivalTime();
        this.requirement = entity.getRequirement();
    }

    public PersonalInfo toEntity(){
        return PersonalInfo.builder()
                .name(name)
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
