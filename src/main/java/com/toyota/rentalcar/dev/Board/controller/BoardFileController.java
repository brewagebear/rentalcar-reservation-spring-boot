package com.toyota.rentalcar.dev.Board.controller;

import com.toyota.rentalcar.dev.commons.utils.UploadFileUtils;
import com.toyota.rentalcar.dev.Board.dto.UploadFileResponse;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.ILoggerFactory;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/file")
public class BoardFileController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        try {
            String savedFilePath = UploadFileUtils.uploadFile(file, request);
            String fileName      = FilenameUtils.getName(savedFilePath);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/v1/board/file/display")
                    .queryParam("fileName", savedFilePath)
                    .toUriString();

            return ResponseEntity.accepted().body(new UploadFileResponse(true, fileName, fileDownloadUri, file.getContentType()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getCause() + " 예상치 못한 오류가 발생하였습니다."));
        }
    }

    @GetMapping("/display")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName, HttpServletRequest request) throws Exception {

        HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName);
        String rootPath = UploadFileUtils.getRootPath(fileName, request) + "/";

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

    @DeleteMapping("")
    public ResponseEntity<?> deleteFile(@RequestParam String fileName, HttpServletRequest request) throws Exception {

        String rootPath = UploadFileUtils.getRootPath(fileName, request);
        logger.info(rootPath + fileName);

        try {
            UploadFileUtils.deleteFile(rootPath + fileName, request);
            return ResponseEntity.ok(new ApiResponse(true, "파일이 성공적으로 삭제되었습니다."));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false,  fileName + "파일이 존재하지 않습니다."));
        }
    }
}
