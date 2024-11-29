package com.wiyata.wiyata.backend.service.global.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.service.global.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final MemberResponse memberResponse;
    private Path dirLocation;

    public FileServiceImpl(MemberResponse memberResponse) {
        this.memberResponse = memberResponse;
    }

    @Override
    public Resource loadFile(String filename) throws FileNotFoundException {
        try {
            Path file = dirLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new CustomException(ErrorConstant.FAILED_READ_FILE);
            }
        } catch (MalformedURLException e) {
            throw new CustomException(ErrorConstant.FILE_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<MemberResponse> saveProfileImg(MultipartFile multipartFile, String userName) {
        String fileName = userName + ".png";
        try {
            Path targetLocation = dirLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successChangeMemberImg());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberResponse.failChangeMemberImg());
        }
    }
}
