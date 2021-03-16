package com.example.server.service.impl;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.example.server.service.FileService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImpl implements FileService{
    ResourceBundle rb = ResourceBundle.getBundle("file");
    String avatar_location = rb.getString("avatar_location");
    String contribution_location = rb.getString("contribution_location");

    private final Path avatarLocationPath = Paths.get(avatar_location);
    private final Path contributionLocationPath = Paths.get(contribution_location);


    @Override
    public String storeAvatar(MultipartFile file, String code) {
        try {
            String path = "";
            if(file.isEmpty()){
                return null;
            }
            Path destinationPath = avatarLocationPath.resolve(Paths.get(code)).normalize().toAbsolutePath();
            path = destinationPath.toString();
            if(!destinationPath.getParent().equals(avatarLocationPath.toAbsolutePath())){
                return null;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Stream<Path> loadAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path load(String filename) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String storeContribution(MultipartFile file, String code) {
        try {
            String path = "";
            if(file.isEmpty()){
                return null;
            }
            Path destinationPath = contributionLocationPath.resolve(Paths.get(code)).normalize().toAbsolutePath();
            path = destinationPath.toString();
            if(!destinationPath.getParent().equals(contributionLocationPath.toAbsolutePath())){
                return null;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }
    
}
