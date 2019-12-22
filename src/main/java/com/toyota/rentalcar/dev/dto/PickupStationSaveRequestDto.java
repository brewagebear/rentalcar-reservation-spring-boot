package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.PickupStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupStationSaveRequestDto {

    private String title;

    public  PickupStation toEntity(){
        return PickupStation.builder()
                .title(title)
                .build();
    }

}
