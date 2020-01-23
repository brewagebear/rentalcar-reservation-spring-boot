package com.toyota.rentalcar.dev.RentalCar.model;

public enum StationType {
    RETURN("1"),
    PICKUP("2");

    private String stationType;

    StationType(String stationType) {
        this.stationType = stationType;
    }
}
