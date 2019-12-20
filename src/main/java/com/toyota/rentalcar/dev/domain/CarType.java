package com.toyota.rentalcar.dev.domain;

public enum CarType {
    GAUM("1"), SAIPAN("2");

    private String rentalLocation;

    CarType(String rentalLocation) {
        this.rentalLocation = rentalLocation;
    }
}
