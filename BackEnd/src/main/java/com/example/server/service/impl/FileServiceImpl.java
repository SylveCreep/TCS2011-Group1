package com.example.server.service.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.example.server.dao.ContributionDao;
import com.example.server.entity.Contribution;
import com.example.server.model.response.FileResponse;
import com.example.server.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{
    @Autowired
    ContributionDao contributionDao;

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
            int f = file.getOriginalFilename().lastIndexOf(".", file.getOriginalFilename().length());
            String olfFileName = file.getOriginalFilename().substring(0, f);
            String fileName = file.getOriginalFilename().replace(olfFileName, "avatar_"+code);
            Path destinationPath = avatarLocationPath.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            if(!destinationPath.getParent().equals(avatarLocationPath.toAbsolutePath())){
                return null;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            path = fileName;
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
    public Path loadAvatarPath(String filename) {
        return avatarLocationPath.resolve(filename).normalize();
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = loadAvatarPath("avatar_"+filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            }
            else {
                 return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FileResponse storeContribution(MultipartFile file, String code) {
        try {
            String path = "";
            if(file.isEmpty()){
                return null;
            }
            int f = file.getOriginalFilename().lastIndexOf(".", file.getOriginalFilename().length());
            String olfFileName = file.getOriginalFilename().substring(0, f);
            String fileName = file.getOriginalFilename().replace(olfFileName, "contribution_"+code);
            Path destinationPath = contributionLocationPath.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            path = destinationPath.toString();
            if(!destinationPath.getParent().equals(contributionLocationPath.toAbsolutePath())){
                return null;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            FileResponse fileResponse = new FileResponse();
            fileResponse.setExtension(path.substring(path.lastIndexOf(".")+1, path.length()));
            fileResponse.setPath(path);
            return fileResponse;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public File loadContributionPath(String code, String extenstion) {
        Path path = contributionLocationPath.resolve(Paths.get("contribution_"+code)).toAbsolutePath();
        String filePath = path.toString()+"."+extenstion;
        File file = new File(filePath);
        return file;
    }

    @Override
    public List<File> loadContributionPathsByUserIdOrMagazineId(Long Id, int type) {
        List<File> files = new ArrayList<>();
        switch (type) {
            case 0:
                List<Contribution> contributionsOfUser = contributionDao.getExistedContributionsByUserId(Id, type);
                for (Contribution contribution : contributionsOfUser) {
                    Path path = contributionLocationPath.resolve(Paths.get("contribution_"+contribution.getCode())).toAbsolutePath();
                    String filePath = path.toString()+"."+contribution.getExtension();
                    File file = new File(filePath);
                    files.add(file);
                }
                break;
            case 1:
                List<Contribution> contributionsOfMagazine = contributionDao.getExistedContributionsByUserId(Id, type);
                for (Contribution contribution : contributionsOfMagazine) {
                    Path path = contributionLocationPath.resolve(Paths.get("contribution_"+contribution.getCode())).toAbsolutePath();
                    String filePath = path.toString()+"."+contribution.getExtension();
                    File file = new File(filePath);
                    files.add(file);
                }
                break;
            default:
                break;
        }
        return files;
    }
    
}
