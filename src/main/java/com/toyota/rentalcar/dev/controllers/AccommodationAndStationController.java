package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.Accommodation;
import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.StationType;
import com.toyota.rentalcar.dev.dto.AccommodationAndStationResponseDto;
import com.toyota.rentalcar.dev.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.services.AccommodationAndStationService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accommodation-station")
public class AccommodationAndStationController {

    private final AccommodationAndStationService accommodationAndStationService;

    @GetMapping("{location}")
    public ResponseEntity<?> getAccommodationAndStationList(@PathVariable RentalLocation location){
        return ResponseEntity.accepted().body(accommodationAndStationService.getAccommodationAndStationList(location));
    }

//    @GetMapping("{location}/{type}")
//    public ResponseEntity<?> getStationByStationType(@PathVariable RentalLocation location, @PathVariable StationType type){
//
//    }

    @PostMapping("/accommodations")
    public ResponseEntity<?> saveAccommodationList(@RequestBody List<AccommodationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllAccommodations(requestDto));
    }

    @PostMapping("/stations")
    public ResponseEntity<?> saveStationList(@RequestBody List<StationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllStations(requestDto));
    }
}

