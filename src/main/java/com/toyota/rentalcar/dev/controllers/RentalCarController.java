package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.services.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vi/car")
public class RentalCarController {

    private final RentalCarService carService;

    @PostMapping("")
    public Long save(@RequestBody RentalCarSaveRequestDto requestDto){
        return carService.save(requestDto);
    }

    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody RentalCarUpdateRequestDto requestDto) {
        return carService.update(id, requestDto);
    }

    @GetMapping("{id}")
    public RentalCarResponseDto findById(@PathVariable Long id){
        return carService.findById(id);
    }

}
