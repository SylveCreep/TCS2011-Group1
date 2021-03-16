package com.example.server.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;

public interface FileService {
    String storeAvatar(MultipartFile file, String code);

    String storeContribution(MultipartFile file, String code);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);
}
