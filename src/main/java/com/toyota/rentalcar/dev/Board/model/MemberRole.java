package com.toyota.rentalcar.dev.Board.model;

public enum MemberRole {

    MANAGER("1"),
    ADMIN("2"),
    MEMBER("3");

    private String role;

    MemberRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
