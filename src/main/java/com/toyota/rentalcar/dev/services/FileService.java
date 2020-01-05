package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.config.FileStorageProperties;
import com.toyota.rentalcar.dev.exceptions.FileStorageException;
import com.toyota.rentalcar.dev.exceptions.MyFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties){
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex){
            throw new FileStorageException("파일이 업로드 될 경로에 디렉토리를 생성하지 못했습니다.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")){
                throw new FileStorageException("파일 이름에 허가되지 않는 문자가 기입되어 있습니다." + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }  catch (IOException e) {
           throw new FileStorageException("파일을 저장할 수 없습니다." + fileName + "다시 한번 시도해주십소.", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            } else {
                throw new MyFileNotFoundException("파일을 찾을 수 없습니다" + fileName);
            }

        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("파일을 찾을 수 없습니다." + fileName, e);
        }

    }
}
