package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.*;
import com.toyota.rentalcar.dev.repositories.RentalCarRepository;
import com.toyota.rentalcar.dev.repositories.RentalCarTagRepository;
import com.toyota.rentalcar.dev.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class RentalCarTagSaveRequestDtoTest {

    @Autowired
    private RentalCarTagRepository carTagRepo;

    @Autowired
    private RentalCarRepository carRepo;

    @Autowired
    private TagRepository tagRepo;

    @Test
    public void 다대다관계저장_불러오기(){
        BigDecimal cost = new BigDecimal(30L);
        RentalCarSaveRequestDto dto = new RentalCarSaveRequestDto();
        dto.setRentalCarType(RentalCarType.GAUM);
        dto.setCarModelName("도요타 테스트");
        dto.setCarImgSource("이미지 테스트");
        dto.setCostPerNight(cost);

        carRepo.save(dto.toEntity());

        RentalCar car = carRepo.findById(1L)
                .orElse(null);

        TagSaveRequestDto dto2 = new TagSaveRequestDto();
        dto2.setTitle("하이");
        tagRepo.save(dto2.toEntity());

        Tag tag = tagRepo.findById(1L)
                .orElse(null);

        RentalCarTagSaveRequestDto dto3 = new RentalCarTagSaveRequestDto();
        dto3.setTag(tag);
        dto3.setCar(car);

        carTagRepo.save(
                dto3.toEntity()
        );

        RentalCarTag rct = carTagRepo.findById(new RentalCarTagId(1L, 1L))
                .orElse(null);
        assertThat(rct).isNotNull();
    }
}