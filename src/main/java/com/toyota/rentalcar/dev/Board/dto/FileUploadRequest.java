package com.toyota.rentalcar.dev.Board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileUploadRequest {

    private String uid;
    private String name;
    private String type;

    @Builder
    public FileUploadRequest(String uid, String name, String type) {
        this.uid = uid;
        this.name = name;
        this.type = type;
    }
}
