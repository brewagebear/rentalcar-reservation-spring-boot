package com.toyota.rentalcar.dev.Board.model;

public enum BoardType {
    FIXED_HEADER("1"),
    NON_FIXED_HEADER("2");

    private String boardType;

    BoardType(String boardType) {
        this.boardType = boardType;
    }
}
