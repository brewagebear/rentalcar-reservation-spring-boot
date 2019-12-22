package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Customer;
import com.toyota.rentalcar.dev.domain.CustomerPickupStation;
import com.toyota.rentalcar.dev.domain.PickupStation;
import com.toyota.rentalcar.dev.repositories.CustomerRepository;
import com.toyota.rentalcar.dev.repositories.PickupStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class CustomerSaveRequestDtoTest {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PickupStationRepository pickupRepo;

    @Test
    public void 사용자저장_불러오기(){

    }

}