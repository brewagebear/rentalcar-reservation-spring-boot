package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.CarReservation;
import com.toyota.rentalcar.dev.domain.PersonalInfo;
import com.toyota.rentalcar.dev.domain.Reservation;
import com.toyota.rentalcar.dev.domain.ReservationStatus;
import com.toyota.rentalcar.dev.dto.*;
import com.toyota.rentalcar.dev.exceptions.BadRequestException;
import com.toyota.rentalcar.dev.repositories.CarReservationRepository;
import com.toyota.rentalcar.dev.repositories.PersonalInfoRepository;
import com.toyota.rentalcar.dev.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository    reservationRepository;
    private final CarReservationRepository carReservationRepository;
    private final PersonalInfoRepository   personalInfoRepository;


    @Transactional
    public CarReservation completeReservation(@Valid PersonalInfoSaveRequestDto personalInfoDto,
                                              @Valid ReservationSaveRequestDto  reservationDto,
                                              @Valid CarReservationSaveRequestDto carReservationDto){
        PersonalInfo personalInfo = personalInfoRepository.save(personalInfoDto.toEntity());
        reservationDto.setPersonalInfo(personalInfo);

        Reservation reservation = reservationRepository.save(reservationDto.toEntity());
        carReservationDto.setReservation(reservation);

        return carReservationRepository.save(carReservationDto.toEntity());
    }

    @Transactional
    public List<CarReservationResponseDto> findAll(){
        List<CarReservationResponseDto> result = new ArrayList<>();
        carReservationRepository.findAll().forEach(carReservation -> {
            result.add(new CarReservationResponseDto(carReservation));
        });
        return result;
    }

    @Transactional
    public CarReservationResponseDto updateConfirmStatus(UUID id, ReservationStatus status) throws BadRequestException {
        Optional<CarReservation> maybeReservation = carReservationRepository.findAllByReservationId(id);
        if(maybeReservation.isPresent()){
            maybeReservation.get().getReservation().update(status);
            return new CarReservationResponseDto(maybeReservation.get());
        }
        throw new BadRequestException("허용되지 않는 행위입니다.");
    }


    @Transactional
    public ReservationResponseDto save(ReservationSaveRequestDto requestDto){
        Reservation entity = reservationRepository.save(requestDto.toEntity());
        return new ReservationResponseDto(entity);
    }

    @Transactional
    public PersonalInfoResponseDto save(PersonalInfoSaveRequestDto requestDto){
        PersonalInfo entity = personalInfoRepository.save(requestDto.toEntity());
        return new PersonalInfoResponseDto(entity);
    }

    @Transactional
    public CarReservation save(CarReservationSaveRequestDto requestDto){
        return  carReservationRepository.save(requestDto.toEntity());
    }
}
