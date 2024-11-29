package com.wiyata.wiyata.backend.service.global.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.service.global.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private Path dirLocation;

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
}
