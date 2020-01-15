package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.RentalLocation;
import com.toyota.rentalcar.dev.domain.RentalCar;
import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class RentalCarSaveRequestDtoTest {
    @Autowired
    private RentalCarRepository carRepo;

    @Test
    public void 렌터카_저장(){
        BigDecimal cost = new BigDecimal(30L);
        RentalCarSaveRequestDto dto = new RentalCarSaveRequestDto();
        dto.setRentalLocation(RentalLocation.GUAM);
        dto.setCarModelName("도요타 테스트");
        dto.setCarImgURL("이미지 테스트");
        dto.setCostPerNight(cost);

        carRepo.save(dto.toEntity());

        RentalCar car = carRepo.findById(1L)
                                .orElse(null);
        assertThat(car).isNotNull();
    }
}