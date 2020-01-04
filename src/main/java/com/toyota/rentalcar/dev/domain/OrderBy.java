package com.toyota.rentalcar.dev.domain;

public enum OrderBy {
    ID("id"), USERID("userName");
    private String OrderByCode;

    private OrderBy(String orderBy) {
        this.OrderByCode = orderBy;
    }

    public String getOrderByCode() {
        return this.OrderByCode;
    }
}
