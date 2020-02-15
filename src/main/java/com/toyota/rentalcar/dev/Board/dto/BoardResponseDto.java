package com.toyota.rentalcar.dev.Board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String userName;
    private int hit;
    private LocalDateTime createDate;

    public BoardResponseDto(Long id, String title, String userName, int hit, LocalDateTime createDate){
        this.id           = id;
        this.title        = title;
        this.userName     = userName;
        this.hit          = hit;
        this.createDate   = createDate;
    }
}
