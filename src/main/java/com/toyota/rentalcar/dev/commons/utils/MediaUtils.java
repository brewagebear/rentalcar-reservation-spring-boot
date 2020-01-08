package com.toyota.rentalcar.dev.commons.utils;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaUtils {

    private static Map<String, MediaType> mediaTypeMap;

    // 클래스 초기화
    static {
        mediaTypeMap = new HashMap<>();
        mediaTypeMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaTypeMap.put("JPEG", MediaType.IMAGE_JPEG);
        mediaTypeMap.put("PNG", MediaType.IMAGE_PNG);
        mediaTypeMap.put("GIF", MediaType.IMAGE_GIF);
    }

    // 파일 타입
    static MediaType getMediaType(String fileName){
        String formatName = getFormatName(fileName);
        return mediaTypeMap.get(formatName);
    }

    // 파일 확장자 추출
    private static String getFormatName(String fileName) {
        // 대문자로 치환하여 Map에 정상적으로 매핑되게끔 함.
        return fileName.substring(fileName.lastIndexOf("." ) + 1).toUpperCase();
    }
}
