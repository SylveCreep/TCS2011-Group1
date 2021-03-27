package com.example.server.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.example.server.model.response.FileResponse;

import org.springframework.core.io.Resource;

public interface FileService {
    String storeAvatar(MultipartFile file, String code);

    FileResponse storeContribution(MultipartFile file, String code);

    Stream<Path> loadAll();

    Path loadAvatarPath(String filename);

    File loadContributionPath(String code, String extenstion);

    Resource loadAsResource(String filename);
}
