package com.toyota.rentalcar.dev.RentalCar.controller;

import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.RentalCar.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/car")
public class RentalCarController {

    private final RentalCarService carService;

    private static final Logger logger = LoggerFactory.getLogger(RentalCarController.class);

    @PostMapping("")
    public Long save(@RequestBody RentalCarSaveRequestDto requestDto){
        return carService.save(requestDto);
    }

//    @PostMapping("/saveAll")
//    public Long saveAll(@RequestBody List<RentalCarSaveRequestDto> requestDtoList){
//        return carService.saveAll(requestDtoList);
//    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody RentalCarUpdateRequestDto requestDto) {
        return carService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        carService.delete(id);
    }

    @GetMapping("/car-detail/{id}")
    public RentalCarResponseDto findById(@PathVariable Long id){
        return carService.findById(id);
    }
}
