package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Accommodation;
import com.toyota.rentalcar.dev.domain.RentalLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "value"})
public class AccommodationSaveRequestDto {

    private Long id;
    private RentalLocation rentalLocation;
    private String name;
    private String value;

    public Accommodation toEntity() {
        return Accommodation.builder()
                .id(id)
                .rentalLocation(rentalLocation)
                .name(name)
                .value(value)
                .build();
    }
}
