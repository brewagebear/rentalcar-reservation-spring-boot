package com.toyota.rentalcar.dev.RentalCar.dto.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private String cause;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, String message, String cause) {
        this.success = success;
        this.message = message;
        this.cause   = cause;
    }
}
