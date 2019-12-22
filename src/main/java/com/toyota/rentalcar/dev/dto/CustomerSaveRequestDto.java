package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Customer;
import com.toyota.rentalcar.dev.domain.PickupStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerSaveRequestDto {

    private String passportName;
    private PickupStation pickupStation;

    public Customer toEntity(){
        return Customer.builder()
                .passportName(passportName)
                .build();
    }

}
