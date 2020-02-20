package com.toyota.rentalcar.dev.RentalCar.controller;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.RentalCar.model.RentalLocation;
import com.toyota.rentalcar.dev.RentalCar.model.StationType;
import com.toyota.rentalcar.dev.RentalCar.dto.AccommodationSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.StationSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.RentalCar.services.AccommodationAndStationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
public class AccommodationAndStationController {

    private final AccommodationAndStationService accommodationAndStationService;

    @ApiOperation(value = "괌/사이판 별 투숙호텔 및 픽업, 반납장소 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string")
    })
    @GetMapping("{rental_place}")
    public ResponseEntity<?> getAccommodationAndStationList(@PathVariable("rental_place") RentalLocation location){
        return ResponseEntity.accepted().body(accommodationAndStationService.getAccommodationAndStationList(location));
    }

    @ApiOperation(value = "괌/사이판 별 픽업, 반납장소 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string"),
            @ApiImplicitParam(name = "station_type", value = "픽업/반납", required = true, dataType = "string")
    })
    @GetMapping("stations/{rental_place}/{station_type}")
    public ResponseEntity<?> getStationByStationType(@PathVariable("rental_place")RentalLocation location, @PathVariable("station_type")StationType type){
        return ResponseEntity.accepted().body(accommodationAndStationService.searchStationWithLocationAndType(location, type));
    }

    @ApiOperation(value = "투숙호텔 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string"),
    })
    @PostMapping("/accommodations")
    public ResponseEntity<?> saveAccommodationList(@RequestBody List<AccommodationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllAccommodations(requestDto));
    }

    @ApiOperation(value = "픽업/반납 장소 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string"),
    })
    @PostMapping("/stations")
    public ResponseEntity<?> saveStationList(@RequestBody List<StationSaveRequestDto> requestDto){
        return ResponseEntity.accepted().body(accommodationAndStationService.saveAllStations(requestDto));
    }

    @ApiOperation(value = "투숙호텔 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string"),
    })
    @PutMapping("/accommodations/{rental_place}")
    public ResponseEntity<?> updateAccommodationList(@PathVariable("rental_place") RentalLocation location,
                                                     @RequestBody List<AccommodationSaveRequestDto> requestDtos){
        accommodationAndStationService.updateAccommodations(location, requestDtos);
        return ResponseEntity.accepted().body(new ApiResponse(true, "성공적으로 업데이트가 되었습니다."));
    }

    @ApiOperation(value = "픽업/반납장소 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rental_place", value = "괌/사이판", required = true, dataType = "string"),
    })
    @PutMapping("/stations/{rental_place}")
    public ResponseEntity<?> updateStationList(@PathVariable("rental_place") RentalLocation location, @RequestBody List<StationSaveRequestDto> requestDtos){
        accommodationAndStationService.updateStations(location, requestDtos);
        return ResponseEntity.accepted().body(new ApiResponse(true, "성공적으로 업데이트가 되었습니다."));
    }
}

