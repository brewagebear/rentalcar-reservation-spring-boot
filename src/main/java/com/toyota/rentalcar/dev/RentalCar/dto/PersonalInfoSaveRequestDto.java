package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.PersonalInfo;
import lombok.Builder;
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

    @Builder
    public PersonalInfoSaveRequestDto(PersonalInfo entity){
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
