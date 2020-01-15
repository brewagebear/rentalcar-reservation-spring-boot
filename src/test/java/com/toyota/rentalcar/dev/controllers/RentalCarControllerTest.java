package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarUpdateRequestDto;
import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalCarControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RentalCarRepository carRepository;

    @After
    public void clear() throws Exception {
        carRepository.deleteAll();
    }

    @Test
    public void 렌터카가_등록된다() throws Exception {

        RentalLocation carType = RentalLocation.GUAM;
        String modelName      = "도요타 김리";
        String imgSource      = "테스트 이미지 경로";
        BigDecimal cost       = BigDecimal.valueOf(20.0);

        RentalCarSaveRequestDto requestDto = RentalCarSaveRequestDto.builder()
                .rentalLocation(carType)
                .carModelName(modelName)
                .carImgURL(imgSource)
                .costPerNight(cost)
                .build();

        String url = "http://localhost:" + port + "/api/v1/car";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<RentalCar> all = carRepository.findAll();

        assertThat(all.get(0).getRentalLocation()).isEqualTo(carType);
        assertThat(all.get(0).getCarModelName()).isEqualTo(modelName);
        assertThat(all.get(0).getCarImgURL()).isEqualTo(imgSource);
        assertThat(all.get(0).getCostPerNight()).isEqualTo(cost);
    }

    @Test
    public void 렌터카가_수정된다() throws Exception {
        //given
        RentalCar savedCar = carRepository.save(RentalCar.builder()
                .rentalLocation(RentalLocation.GUAM)
                .carModelName("도요타 김리")
                .carImgURL("테스트 경로")
                .costPerNight(BigDecimal.valueOf(20.00))
                .build()
        );

        Long updateId = savedCar.getId();
        String expectedName      = "도요타 야리스";
        String expectedImgSource = "테스트 경로 2";
        BigDecimal expectedCost      = BigDecimal.valueOf(20.00);

        RentalCarUpdateRequestDto requestDto = RentalCarUpdateRequestDto.builder()
                .carModelName(expectedName)
                .carImgSource(expectedImgSource)
                .costPerNight(expectedCost)
                .build();

        String url = "http://localhost:" + port + "/api/v1/car/" + updateId;

        HttpEntity<RentalCarUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<RentalCar> all = carRepository.findAll();
        assertThat(all.get(0).getCarModelName()).isEqualTo(expectedName);
        assertThat(all.get(0).getCarImgURL()).isEqualTo(expectedImgSource);
        assertThat(all.get(0).getCostPerNight()).isEqualTo(expectedCost);

    }

}