package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.dto.RentalCarResponseDto;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
