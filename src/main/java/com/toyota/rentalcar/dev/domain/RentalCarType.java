package com.toyota.rentalcar.dev.domain;

public enum RentalCarType {

    GAUM("1"),
    SAIPAN("2");

    private String rentalLocation;

    RentalCarType(String rentalLocation) {
        this.rentalLocation = rentalLocation;
    }
}
