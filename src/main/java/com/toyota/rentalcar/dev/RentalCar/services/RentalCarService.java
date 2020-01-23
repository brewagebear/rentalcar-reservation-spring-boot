package com.toyota.rentalcar.dev.RentalCar.services;

import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.RentalCar.repositories.RentalCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 없습니다. id=" + id));
        rentalCar.update(requestDto);
        return id;
    }

    public RentalCarResponseDto findById(Long id){
        RentalCar entity = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 없습니다. id=" + id));

        return new RentalCarResponseDto(entity);
    }

    public RentalCar findOne(Long id){
        return carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 존재하지 않습니다. id=" + id));
    }

    public void saveAll(List<RentalCarSaveRequestDto> requestDto){

    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

//    @Transactional
//    public Long delete(Long id, RentalCarUpdateRequestDto requestDto) {
//        RentalCar entity = carRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 존재하지 않습니다. id=" + id));
//
//        return carRepository.delete(entity);
//    }

//    public Long saveWithTag(String id, RentalCarSaveRequestDto requestDto) {
//        RentalCar entity = carRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 존재하지 않습니다. id=" + id));
//        return carRepository.save(requestDto.toEntity()).getId();
//    }
}
