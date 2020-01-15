package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.BorrowedDate;
import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.dto.BorrowedDateSaveRequestDto;
import com.toyota.rentalcar.dev.dto.InvalidDateSaveRequestDto;
import com.toyota.rentalcar.dev.repositories.BorrowedDateRepository;
import com.toyota.rentalcar.dev.repositories.InvalidDateRepository;
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

    private final BorrowedDateRepository borrowedDateRepository;
    private final InvalidDateRepository  invalidDateRepository;

    private static final Logger logger = LoggerFactory.getLogger(DateService.class);

    @Transactional
    public BorrowedDate findByCarId(Long id){
        return borrowedDateRepository.findByCarId(id);
    }

    @Transactional
    public Long saveBorrowedDate(BorrowedDateSaveRequestDto requestDto){
        logger.info(requestDto.getEndDate().toString());
        return borrowedDateRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public List<RentalCar> checkAvailableCars(Calendar startDate, Calendar endDate){
        return invalidDateRepository.checkAvailableCars(startDate, endDate);
    }

    @Transactional
    public Long saveInvalidDate(InvalidDateSaveRequestDto requestDto){
        return invalidDateRepository.save(requestDto.toEntity()).getId();
    }

}
