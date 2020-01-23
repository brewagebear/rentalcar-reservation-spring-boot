package com.toyota.rentalcar.dev.Board.dto;

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

    public UploadFileResponse(UploadFileResponse resp) {
        this.uploaded   = resp.getUploaded();
        this.fileName   = resp.getFileName();
        this.uri        = resp.getUri();
        this.fileType   = resp.getFileType();
    }
}
