package com.toyota.rentalcar.dev.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadFileResponse {
    private Boolean uploaded;
    private String fileName;
    private String uri;
    private String fileType;

    public UploadFileResponse(Boolean uploaded,String fileName, String fileDownloadUri, String fileType){
        this.uploaded        = uploaded;
        this.fileName        = fileName;
        this.uri             = fileDownloadUri;
        this.fileType        = fileType;
    }
}
