package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Customer;
import com.toyota.rentalcar.dev.domain.CustomerPickupStation;
import com.toyota.rentalcar.dev.domain.PickupStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerPickupSaveRequestDto {

    private Customer      customer;
    private PickupStation pickupStation;

    public CustomerPickupStation toEntity(){
        return CustomerPickupStation.builder()
                .customer(customer)
                .pickupStation(pickupStation)
                .build();
    }
}
