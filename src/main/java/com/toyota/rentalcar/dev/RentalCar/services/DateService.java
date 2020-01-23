package com.toyota.rentalcar.dev.RentalCar.services;

import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import com.toyota.rentalcar.dev.RentalCar.dto.InvalidDateSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.repositories.InvalidDateRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateService {

    private final InvalidDateRepository  invalidDateRepository;

    private static final Logger logger = LoggerFactory.getLogger(DateService.class);

    @Transactional
    public List<RentalCar> checkAvailableCars(Calendar startDate, Calendar endDate){
        return invalidDateRepository.checkAvailableCars(startDate, endDate);
    }

    @Transactional
    public Long saveInvalidDate(InvalidDateSaveRequestDto requestDto){
        return invalidDateRepository.save(requestDto.toEntity()).getId();
    }
}
