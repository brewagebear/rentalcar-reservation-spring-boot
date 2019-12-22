package com.toyota.rentalcar.dev.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@NoArgsConstructor
public class ReservationDates {

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate rentalStartDate;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate rentalEndDate;

    @Column(nullable = false)
    private boolean lateReturn = false;

    @Column(nullable = false)
    private boolean policyAcknowledged = false;

    @Builder
    public ReservationDates(LocalDate rentalStartDate, LocalDate rentalEndDate,
                            boolean lateReturn, boolean policyAcknowledged){

        this.rentalStartDate    = rentalStartDate;
        this.rentalEndDate      = rentalEndDate;
        this.lateReturn         = lateReturn;
        this.policyAcknowledged = policyAcknowledged;
    }

    public boolean isLateReturn() { return lateReturn; }

    public void setLateReturn(boolean lateReturn) {
        this.lateReturn = lateReturn;
    }

    public boolean isPolicyAcknowledged(){ return  policyAcknowledged; }
    public void setPolicyAcknowledged(boolean policyAcknowledged) { this.policyAcknowledged = policyAcknowledged; }

    public long totalNights(){
        if(rentalStartDate == null || rentalEndDate == null){
            return 0;
        }
        return ChronoUnit.DAYS.between(rentalStartDate, rentalStartDate);
    }
}
