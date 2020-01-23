package com.toyota.rentalcar.dev.RentalCar.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Getter
@Embeddable
@NoArgsConstructor
public class ReservationDates implements Serializable {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @Builder
    public ReservationDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        this.startDate = startDate;
        this.endDate   = endDate;
        this.startTime = startTime;
        this.endTime   = endTime;
    }

    public long totalNights(){
        if(startDate == null || endDate == null){
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public Optional<ValidationError> vaildate(LocalDate now){
        if(startDate == null){
            return Optional.of(new ValidationError("startDate.missing", "대여 날짜를 다시 확인해주세요."));
        } else if (endDate == null){
            return Optional.of(new ValidationError("endDate.missing", "반납 날짜를 다시 확인해주세요."));
        } else if (startDate.isBefore(now)) {
            return Optional.of(new ValidationError("endDate.future", "대여 날짜는 현재 시간 이후여야 합니다."));
        } else if (endDate.isBefore(startDate)) {
            return Optional.of(new ValidationError("endDate.afterStartDate", "반납 날짜는 대여 날짜 이후여야 합니다."));
        } else if (totalNights() < 1){
            return Optional.of(new ValidationError("endDate.minNights", "예약은 최소 1일 이상 가능합니다."));
        }
        return Optional.empty();
    }

    public static class ValidationError {
        private String code;
        private String reason;

        public ValidationError(String code, String reason) {
            this.code = code;
            this.reason = reason;
        }

        public String getCode() {
            return code;
        }
        public String getReason() {
            return reason;
        }
    }

    @Override
    public String toString(){
        return "ReservationDates{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime="  + endTime +
                '}';
    }
}
