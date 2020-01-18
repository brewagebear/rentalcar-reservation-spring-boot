package com.toyota.rentalcar.dev.domain;

public enum ReservationStatus {

    COMPLETED_PAYMENT("1"),
    ALLOWED("2"),
    DENIED("3"),
    CANCEL_REQUIREMENT("4");


    private String status;
    ReservationStatus(String status) {
        this.status = status;
    }
}
