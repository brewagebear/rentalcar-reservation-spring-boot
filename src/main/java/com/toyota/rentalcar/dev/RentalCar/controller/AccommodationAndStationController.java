package com.toyota.rentalcar.dev.RentalCar.controller;

import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import com.toyota.rentalcar.dev.RentalCar.model.StationType;
import com.toyota.rentalcar.dev.RentalCar.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.RentalCar.services.AccommodationAndStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
public class AccommodationAndStationController {

    private final AccommodationAndStationService accommodationAndStationService;

    @GetMapping("{location}")
    public ResponseEntity<?> getAccommodationAndStationList(@PathVariable RentalLocation location){
        return ResponseEntity.accepted().body(accommodationAndStationService.getAccommodationAndStationList(location));
    }

    @GetMapping("stations/{location}/{type}")
    public ResponseEntity<?> getStationByStationType(@PathVariable RentalLocation location, @PathVariable StationType type){
        return ResponseEntity.accepted().body(accommodationAndStationService.searchStationWithLocationAndType(location, type));
    }

    @PostMapping("/accommodations")
    public ResponseEntity<?> saveAccommodationList(@RequestBody List<AccommodationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllAccommodations(requestDto));
    }

    @PostMapping("/stations")
    public ResponseEntity<?> saveStationList(@RequestBody List<StationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllStations(requestDto));
    }

    @PutMapping("/accommodations/{location}")
    public ResponseEntity<?> updateAccommodationList(@PathVariable RentalLocation location, @RequestBody List<AccommodationSaveRequestDto> requestDtos){
        accommodationAndStationService.updateAccommodations(location, requestDtos);
        return ResponseEntity.accepted().body(new ApiResponse(true, "성공적으로 업데이트가 되었습니다."));
    }

    @PutMapping("/stations/{location}")
    public ResponseEntity<?> updateStationList(@PathVariable RentalLocation location, @RequestBody List<StationSaveRequestDto> requestDtos){
        accommodationAndStationService.updateStations(location, requestDtos);
        return ResponseEntity.accepted().body(new ApiResponse(true, "성공적으로 업데이트가 되었습니다."));
    }
}

