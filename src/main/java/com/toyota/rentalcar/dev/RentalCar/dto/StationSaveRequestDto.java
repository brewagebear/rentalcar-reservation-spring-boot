package com.toyota.rentalcar.dev.RentalCar.dto;

import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import com.toyota.rentalcar.dev.RentalCar.model.Station;
import com.toyota.rentalcar.dev.RentalCar.model.StationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "value"})
public class StationSaveRequestDto {

    private Long           id;
    private RentalLocation rentalLocation;
    private StationType    stationType;
    private String name;
    private String value;

    @Builder
    public StationSaveRequestDto(Long id,
                                 RentalLocation rentalLocation,
                                 StationType stationType,
                                 String name,
                                 String value){
        this.id             = id;
        this.rentalLocation = rentalLocation;
        this.stationType    = stationType;
        this.name           = name;
        this.value          = value;
    }

    public Station toEntity(){
        return Station.builder()
                .id(id)
                .location(rentalLocation)
                .stationType(stationType)
                .name(name)
                .value(value)
                .build();
    }

}
