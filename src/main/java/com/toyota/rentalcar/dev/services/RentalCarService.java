package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class RentalCarService {

    private final RentalCarRepository carRepository;

    @Transactional
    public Long save(RentalCarSaveRequestDto requestDto){
        return carRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, RentalCarUpdateRequestDto requestDto){
        RentalCar rentalCar = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        rentalCar.update(requestDto.getCarModelName(), requestDto.getCarImgSource(), requestDto.getCostPerNight());
        return id;
    }

    public RentalCarResponseDto findById(Long id){
        RentalCar entity = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new RentalCarResponseDto(entity);
    }

}
