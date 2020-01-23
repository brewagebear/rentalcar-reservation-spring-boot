package com.toyota.rentalcar.dev.RentalCar.dto;


import com.toyota.rentalcar.dev.RentalCar.model.Accommodation;
import com.toyota.rentalcar.dev.RentalCar.model.Station;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class AccommodationAndStationResponseDto {

    private List<Accommodation> accommodations = new ArrayList<>();
    private Map<String, List<Station>> stationListMap = new HashMap<>();

    public AccommodationAndStationResponseDto(List<Accommodation> accommodations, List<Station> pickupList, List<Station> returnList){
        this.accommodations = accommodations;
        this.stationListMap.put("pickupStations", pickupList);
        this.stationListMap.put("returnStations", returnList);
    }
}
