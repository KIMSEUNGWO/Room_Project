package project.study.controller.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.study.domain.ImageFileEntity;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUpload {

    private final FileStorage fileStorage;
    private final FileUploadRepository fileUploadRepository;

    public void saveFile(@Nullable MultipartFile imageFile, final FileUploadType fileType, final ImageFileEntity parentEntity) {
        FileUploadDto fileUploadDto = FileUploadDto.builder().parent(parentEntity).type(fileType).build();

        if (imageFile == null) {
            fileUploadRepository.saveImage(fileUploadDto);
            return;
        }

        if (isNotImage(imageFile)) return;

        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = createFileName(originalFileName);

        try {
            fileStorage.uploadFile(imageFile, fileType, storeFileName);
        } catch (IOException e) {
            log.error("saveFile transferTo error = {}", imageFile.getName());
            return;
        }

        fileUploadDto.setImage(originalFileName, storeFileName);
        fileUploadRepository.saveImage(fileUploadDto);
    }

    public void editFile(@Nullable MultipartFile imageFile, FileUploadType fileType, ImageFileEntity parentEntity) {
        FileUploadDto fileUploadDto = FileUploadDto.builder().parent(parentEntity).type(fileType).build();

        if (imageFile == null) {
            fileUploadRepository.editImage(fileUploadDto);
            return;
        }

        if (isNotImage(imageFile)) return;

        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = createFileName(originalFileName);

        try {
            fileStorage.uploadFile(imageFile, fileType, storeFileName);
        } catch (IOException e) {
            log.error("editFile transferTo error = {}", imageFile.getName());
            return;
        }

        // 이전 이미지 파일 삭제
        fileStorage.deleteFile(fileType, parentEntity);

        fileUploadDto.setImage(originalFileName, storeFileName);
        fileUploadRepository.editImage(fileUploadDto);
    }



    private boolean isNotImage(MultipartFile file) {
        return file.getContentType() == null || !file.getContentType().startsWith("image/");
    }

    @NotNull
    private String createFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();

        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos);

        return uuid + ext;
    }

}