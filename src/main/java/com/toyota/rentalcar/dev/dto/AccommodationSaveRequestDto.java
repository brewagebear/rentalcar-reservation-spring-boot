package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Accommodation;
import com.toyota.rentalcar.dev.domain.RentalLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationSaveRequestDto {

    private RentalLocation rentalLocation;
    private String name;
    private String value;

    public Accommodation toEntity() {
        return Accommodation.builder()
                .rentalLocation(rentalLocation)
                .name(name)
                .value(value)
                .build();
    }
}
