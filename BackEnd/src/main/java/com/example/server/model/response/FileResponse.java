package com.example.server.model.response;

import java.nio.file.Path;

public class FileResponse {
    private String path;
    private String extension;
    private Path filePath;
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public FileResponse() {
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public FileResponse(String path, String extension, Path filePath) {
        this.path = path;
        this.extension = extension;
        this.filePath = filePath;
    }

    

}
