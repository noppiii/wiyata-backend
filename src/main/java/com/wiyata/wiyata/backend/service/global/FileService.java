package com.wiyata.wiyata.backend.service.global;

import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface FileService {

    Resource loadFile(String filename) throws FileNotFoundException;

    ResponseEntity<MemberResponse> saveProfileImg(MultipartFile multipartFile, String userName);
}
