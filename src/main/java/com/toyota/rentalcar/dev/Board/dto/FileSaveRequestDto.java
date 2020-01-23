package com.toyota.rentalcar.dev.Board.dto;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileSaveRequestDto {

    private Board board;
    private String fileName;
    private String uri;

    public File toEntity(){
        return File.builder()
                .board(board)
                .fileName(fileName)
                .uri(uri)
                .build();
    }
}
