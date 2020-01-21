package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.Accommodation;
import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.Station;
import com.toyota.rentalcar.dev.domain.StationType;
import com.toyota.rentalcar.dev.dto.AccommodationAndStationResponseDto;
import com.toyota.rentalcar.dev.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.repositories.AccommodationRepository;
import com.toyota.rentalcar.dev.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccommodationAndStationService {

    private final AccommodationRepository accommodationRepository;
    private final StationRepository       stationRepository;

    @Transactional
    public AccommodationAndStationResponseDto getAccommodationAndStationList(RentalLocation rentalLocation){
        List<Accommodation> accommodations = accommodationRepository.findAccommodationsByRentalLocation(rentalLocation);
        List<Station> pickupStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.PICKUP);
        List<Station> returnStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.RETURN);
        return new AccommodationAndStationResponseDto(accommodations, pickupStation, returnStation);
    }

    @Transactional
    public List<Station> saveAllStations(List<StationSaveRequestDto> requestDto){
        Iterator<StationSaveRequestDto> iterator = requestDto.iterator();
        List<Station> stations = new ArrayList<>();
        while(iterator.hasNext()){
            stations.add(iterator.next().toEntity());
        }
        return stationRepository.saveAll(stations);
    }

    @Transactional
    public List<Accommodation> saveAllAccommodations(List<AccommodationSaveRequestDto> requestDto){
        Iterator<AccommodationSaveRequestDto> iterator = requestDto.iterator();
        List<Accommodation> accommodations = new ArrayList<>();
        while(iterator.hasNext()) {
            accommodations.add(iterator.next().toEntity());
        }
        return accommodationRepository.saveAll(accommodations);
    }
}
