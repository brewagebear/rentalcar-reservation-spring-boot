package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.commons.utils.UploadFileUtils;
import com.toyota.rentalcar.dev.dto.UploadFileResponse;
import com.toyota.rentalcar.dev.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/file")
public class BoardFileController {

    private final FileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(BoardFileController.class);

    @PostMapping(value = "/upload")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        try {
            String savedFilePath = UploadFileUtils.uploadFile(file, request);
            String fileName      = FilenameUtils.getName(savedFilePath);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/v1/board/file/display")
                    .queryParam("fileName", savedFilePath)
                    .toUriString();

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/display")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName, HttpServletRequest request) throws Exception {

        HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName);
        String rootPath = UploadFileUtils.getRootPath(fileName, request) + "/";
        logger.info(rootPath);

        Resource resource = UploadFileUtils.getResource(fileName, rootPath);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e){
            logger.info("파일 타입이 정의되지 않았습니다.");
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }
        logger.info(httpHeaders.toString());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(String.valueOf(httpHeaders))
                .body(resource);
    }
}
