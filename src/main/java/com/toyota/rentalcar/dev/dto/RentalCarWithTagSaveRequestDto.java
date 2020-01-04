package com.toyota.rentalcar.dev.dto;


import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.domain.RentalCarTag;
import com.toyota.rentalcar.dev.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RentalCarWithTagSaveRequestDto {

    private RentalCar car;
    private Tag tag;

    RentalCarTag toEntity(){
        return RentalCarTag.builder()
                .car(car)
                .tag(tag)
                .build();
    }
}
