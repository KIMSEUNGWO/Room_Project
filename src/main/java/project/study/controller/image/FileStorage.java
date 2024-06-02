package project.study.controller.image;

import org.springframework.web.multipart.MultipartFile;
import project.study.domain.ImageFileEntity;

import java.io.IOException;

public interface FileStorage {

    void uploadFile(MultipartFile imageFile, FileUploadType fileType, String storeFileName) throws IOException;
    void deleteFile(FileUploadType type, ImageFileEntity parent);
}
