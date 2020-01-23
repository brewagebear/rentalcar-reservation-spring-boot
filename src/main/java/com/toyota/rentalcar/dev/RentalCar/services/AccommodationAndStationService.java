package com.toyota.rentalcar.dev.RentalCar.services;

import com.toyota.rentalcar.dev.RentalCar.model.Accommodation;
import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import com.toyota.rentalcar.dev.RentalCar.model.Station;
import com.toyota.rentalcar.dev.RentalCar.model.StationType;
import com.toyota.rentalcar.dev.RentalCar.dto.AccommodationAndStationResponseDto;
import com.toyota.rentalcar.dev.RentalCar.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.commons.exceptions.BadRequestException;
import com.toyota.rentalcar.dev.RentalCar.repositories.AccommodationRepository;
import com.toyota.rentalcar.dev.RentalCar.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
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
    public void updateStations(RentalLocation rentalLocation, List<StationSaveRequestDto> requestDtos){
        List<Station> stations   = stationRepository.findAllByRentalLocation(rentalLocation);
        List<Station> resultList = new ArrayList<>();
        for(StationSaveRequestDto stationSaveRequestDto : requestDtos){
            resultList.add(stationSaveRequestDto.toEntity());
        }

        Set<Station> temp = new HashSet<>(stations);
        stations.removeAll(resultList);
        resultList.removeAll(stations);
        temp.clear();

        for(Station station : resultList){
            Station station1 = stationRepository.getOne(station.getId());
            station1.update(station.getRentalLocation(), station.getStationType(), station.getName(), station.getValue());
        }

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

    public List<Station> searchStationWithLocationAndType(RentalLocation location, StationType type) {
        Optional<List<Station>> maybeStations = stationRepository.findAllByRentalLocationAndStationType(location, type);
        if(maybeStations.isPresent()){
            return maybeStations.get();
        } else {
            throw new BadRequestException("허용되지 않는 행위 입니다.");
        }
    }
}
