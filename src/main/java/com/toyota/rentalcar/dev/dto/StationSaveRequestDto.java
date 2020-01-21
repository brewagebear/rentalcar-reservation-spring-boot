package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.Station;
import com.toyota.rentalcar.dev.domain.StationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationSaveRequestDto {

    private RentalLocation rentalLocation;
    private StationType    stationType;
    private String name;
    private String value;

    @Builder
    public StationSaveRequestDto(RentalLocation rentalLocation,
                                 StationType stationType,
                                 String name,
                                 String value){
        this.rentalLocation = rentalLocation;
        this.stationType    = stationType;
        this.name           = name;
        this.value          = value;
    }

    public Station toEntity(){
        return Station.builder()
                .location(rentalLocation)
                .stationType(stationType)
                .name(name)
                .value(value)
                .build();
    }

}
