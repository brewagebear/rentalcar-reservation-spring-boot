package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.ReservationDates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDatesSaveRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;

    public ReservationDates toEntity(){
        return ReservationDates.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
