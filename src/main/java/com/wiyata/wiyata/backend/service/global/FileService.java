package com.wiyata.wiyata.backend.service.global;

import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;

public interface FileService {

    Resource loadFile(String filename) throws FileNotFoundException;
}
