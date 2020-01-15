package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.*;
import com.toyota.rentalcar.dev.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Commit
//class PersonalInfoPickupSaveRequestDtoTest {
//
//    @Autowired
//    private CustomerRepository customerRepo;
//
//    @Autowired
//    private PickupStationRepository pickupRepo;
//
//    @Autowired
//    private CustomerPickupStationRepository customerPickRepo;
//
//    @Test
//    public void 다대다_저장_불러오기(){
//        CustomerSaveRequestDto dto = new CustomerSaveRequestDto();
//        dto.setPassportName("신수웅");
//        customerRepo.save(dto.toEntity());
//
//        PersonalInfo personalInfo = customerRepo.findById(1L)
//                .orElse(null);
//
//        PickupStationSaveRequestDto dto2 = new PickupStationSaveRequestDto();
//        dto2.setTitle("하이");
//        pickupRepo.save(dto2.toEntity());
//
//        PickupStation pickup = pickupRepo.findById(1L)
//                .orElse(null);
//
//        CustomerPickupSaveRequestDto dto3 = new CustomerPickupSaveRequestDto();
//        dto3.setPersonalInfo(personalInfo);
//        dto3.setPickupStation(pickup);
//
//        customerPickRepo.save(
//                dto3.toEntity()
//        );
//
//        CustomerPickupStation cps = customerPickRepo.findById(new CustomerPickupStationId(1L, 1L))
//                .orElse(null);
//        assertThat(cps).isNotNull();
//    }
//}