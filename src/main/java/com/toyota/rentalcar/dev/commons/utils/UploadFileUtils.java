package com.toyota.rentalcar.dev.commons.utils;

import com.toyota.rentalcar.dev.commons.config.FileStorageProperties;
import com.toyota.rentalcar.dev.commons.exceptions.MyFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@RequiredArgsConstructor
public class UploadFileUtils {

    private static FileStorageProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

    public static String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {

        String originalFileName = file.getOriginalFilename();
        byte[] fileData = file.getBytes();

        // 1. 파일명 중복 방지 처리를 위하여 UUID로 파일명 변경
        String uuidFileName =  getUuidFileName(originalFileName);

        // 2. 파일 업로드 경로 설정
        String rootPath = getRootPath(uuidFileName, request);
        String datePath = getDatePath(rootPath);

        // 3. 서버에 파일 저장
        File target = new File(rootPath + datePath, uuidFileName); // 객체 생성
        FileCopyUtils.copy(fileData, target); // 파일 객체에 파일 데이터 복사

        return replaceSavedFilePath(datePath, uuidFileName);
    }

    public static void deleteFile(String fileName, HttpServletRequest request) throws IOException {

        String rootPath = getRootPath(fileName, request); // 기본 경로 추출

        // 1. 원본 이미지 파일 삭제
        MediaType mediaType = MediaUtils.getMediaType(fileName);
        if (mediaType != null) {
            String originalImg = fileName.substring(0, 14) + fileName.substring(14);
            new File(originalImg.replace('/', File.separatorChar)).delete();
        }

        // 2. 파일 삭제(썸네일이미지 or 일반파일)
        new File(rootPath + fileName.replace('/', File.separatorChar)).delete();
    }

    // 파일 출력을 위한 HttpHeader 설정
    public static HttpHeaders getHttpHeaders(String fileName) throws Exception{

        MediaType mediaType = MediaUtils.getMediaType(fileName);
        HttpHeaders httpHeaders = new HttpHeaders();

        // 이미지 파일일 경우
        if(mediaType != null){
            httpHeaders.setContentType(mediaType);
            return httpHeaders;
        }

        // 이미지 파일이 아닐 경우
        fileName = fileName.substring(fileName.indexOf("_" ) + 1); // UUID 제거
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 한글 인코딩 처리
        httpHeaders.add("Content-Disposition",
                        "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
                        "ISO-8859-1") + "\"");

        return httpHeaders;
    }

    // 기본 경로 추출
    public static String getRootPath(String fileName, HttpServletRequest request) throws IOException {

        String rootPath = "resources/upload";
        MediaType mediaType = MediaUtils.getMediaType(fileName); // 파일타입 확인
        if (mediaType != null) {
            String realPath = "/images"; // 이미지 파일 경로
            if (!new File(rootPath + realPath).exists()) {
                Files.createDirectories(Paths.get(rootPath + realPath));
            }
            return rootPath+realPath;
        }

        return request.getSession().getServletContext().getRealPath(rootPath + "/files"); // 일반파일 경로
    }

    //날짜 폴더명 추출
    private static String getDatePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();
        String yearPath   = File.separator + calendar.get(Calendar.YEAR);
        String monthPath  = yearPath  + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String datePath   = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDateDir(uploadPath, yearPath, monthPath, datePath);
        return datePath;
    }

    // 날짜별 폴더 생성
    private static void makeDateDir(String uploadPath, String ... paths) {

        // 날짜별 폴더가 이미 존재하면 메소드 종료
        if (new File(uploadPath + paths[paths.length -1]).exists())
            return;

        for (String path : paths){
            File dirPath = new File(uploadPath + path);
            if(!dirPath.exists())
                dirPath.mkdir();
        }
    }

    // 파일 저장 경로 치환
    private static String replaceSavedFilePath(String datePath, String fileName) {
        String savedFilePath = datePath + File.separator + fileName;
        return savedFilePath.replace(File.separatorChar, '/');
    }

    // 파일명 중복방지 처리
    private static String getUuidFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    // 파일 가져오기 처리
    public static Resource getResource(String fileName, String savedPath){
        try {
            Path filePath = Paths.get(savedPath + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            logger.info(resource.getFilename());
            if(resource.exists()){
                return resource;
            } else {
                throw new MyFileNotFoundException("파일을 찾을 수 없습니다" + fileName);
            }
        } catch (Exception e){
            throw new MyFileNotFoundException("파일을 찾을 수 없습니다." + fileName, e);
        }
    }

}
