package com.toyota.rentalcar.dev.dto;

import com.toyota.rentalcar.dev.domain.Board;
import com.toyota.rentalcar.dev.domain.FileEntity;
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

    public FileEntity toEntity(){
        return FileEntity.builder()
                .board(board)
                .fileName(fileName)
                .uri(uri)
                .build();
    }
}
