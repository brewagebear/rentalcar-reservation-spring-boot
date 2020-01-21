package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.*;
import com.toyota.rentalcar.dev.dto.AccommodationAndStationResponseDto;
import com.toyota.rentalcar.dev.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.repositories.AccommodationRepository;
import com.toyota.rentalcar.dev.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccommodationAndStationService {

    private final AccommodationRepository accommodationRepository;
    private final StationRepository       stationRepository;


    /***
     * Dto에서 바인딩 처리를 하는 것이 아니라, 바인딩 처리를 Service단에 해서 Push할 예정
     * @param rentalLocation
     * @return AccommodationAndStationResponseDto(List<Accommodation> accommodations,
     *                                            Map<String, List<Station>> pickupStation,
     *                                            Map<String, List<Station>> returnStation)
     */
    @Transactional
    public AccommodationAndStationResponseDto getAccommodationAndStationList(RentalLocation rentalLocation){

        List<Accommodation> accommodations = accommodationRepository.findAccommodationsByRentalLocation(rentalLocation);
        List<Station> pickupStation = new ArrayList<>();
        List<Station> returnStation = new ArrayList<>();

        Optional<List<Station>> maybePickupStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.PICKUP);
        maybePickupStation.ifPresent(pickupStation::addAll);

        Optional<List<Station>> maybeReturnStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.RETURN);
        maybeReturnStation.ifPresent(returnStation::addAll);

        return new AccommodationAndStationResponseDto(accommodations, pickupStation, returnStation);
    }

    @Transactional
    public void updateAccommodations(RentalLocation rentalLocation, List<AccommodationSaveRequestDto> requestDto){

        List<Accommodation> sourceList = accommodationRepository.findAccommodationsByRentalLocation(rentalLocation);
        List<Accommodation> destList   = new ArrayList<>();
        for (AccommodationSaveRequestDto accommodationSaveRequestDto : requestDto) {
            destList.add(accommodationSaveRequestDto.toEntity());
        }

        Set<Accommodation> temp = new HashSet<>(sourceList);
        sourceList.removeAll(destList);
        destList.removeAll(temp);
        temp.clear();

        for(Accommodation accommodation : destList){
            Accommodation accommodation1 = accommodationRepository.getOne(accommodation.getId());
            accommodation1.update(accommodation.getRentalLocation(), accommodation.getName(), accommodation.getValue());
        }
    }

    @Transactional
    public void updateStations(RentalLocation rentalLocation, List<StationSaveRequestDto> requestDto){
        Optional<List<Station>> maybePickupStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.PICKUP);
        maybePickupStation.ifPresent(stations -> stations.forEach(station -> station.update(station.getRentalLocation(), station.getStationType(), station.getName(), station.getValue())));

        Optional<List<Station>> maybeReturnStation = stationRepository.findAllByRentalLocationAndStationType(rentalLocation, StationType.RETURN);
        maybeReturnStation.ifPresent(stations -> stations.forEach(station -> station.update(station.getRentalLocation(), station.getStationType(), station.getName(), station.getValue())));
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
