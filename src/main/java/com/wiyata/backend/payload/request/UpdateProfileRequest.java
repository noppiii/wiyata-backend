package com.wiyata.backend.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UpdateProfileRequest {

    private MultipartFile profileImg;
    private String nickname;
    private String intro;
    private List<String> tags;
}
