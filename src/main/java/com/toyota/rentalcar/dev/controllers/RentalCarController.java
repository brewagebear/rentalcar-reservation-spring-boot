package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody RentalCarUpdateRequestDto requestDto) {
        return carService.update(id, requestDto);
    }

    @GetMapping("/car-detail/{id}")
    public RentalCarResponseDto findById(@PathVariable Long id){
        return carService.findById(id);
    }
}
