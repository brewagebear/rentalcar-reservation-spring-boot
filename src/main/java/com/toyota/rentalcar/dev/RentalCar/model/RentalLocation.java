package com.toyota.rentalcar.dev.RentalCar.model;

public enum RentalLocation {

    GUAM("1"),
    SAIPAN("2");

    private String rentalLocation;

    RentalLocation(String rentalLocation) {
        this.rentalLocation = rentalLocation;
    }
}
