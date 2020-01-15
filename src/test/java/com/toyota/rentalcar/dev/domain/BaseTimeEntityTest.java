package com.toyota.rentalcar.dev.domain;

import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BaseTimeEntityTest {

    @Autowired
    RentalCarRepository carRepository;

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        carRepository.save(RentalCar.builder()
                .rentalLocation(RentalLocation.GUAM)
                .carModelName("도요타 야리스")
                .costPerNight(BigDecimal.valueOf(20.00))
                .carImgURL("테스트 경로")
                .build());

        //when
        List<RentalCar> rentalCarList = carRepository.findAll();

        //then
        RentalCar rentalCar = rentalCarList.get(0);

        System.out.println(">>>>>> createDate=" + rentalCar.getCreatedDate() + "modifiedDate=" + rentalCar.getModifiedDate());

        assertThat(rentalCar.getCreatedDate()).isAfter(now);
        assertThat(rentalCar.getModifiedDate()).isAfter(now);
    }

}