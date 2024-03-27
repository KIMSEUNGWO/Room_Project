package project.study.controller.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.study.domain.ImageFileEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUpload {

    @Value("${file.dir}")
    private String fileDir;

    private final FileUploadRepository fileUploadRepository;

    public void saveFile(@Nullable MultipartFile imageFile, FileUploadType fileType, ImageFileEntity parentEntity) {
        if (imageFile == null) {
            FileUploadDto fileUploadDto = FileUploadDto.builder()
                            .parent(parentEntity)
                            .type(fileType)
                            .build();
            fileUploadRepository.saveImage(fileUploadDto);
            return;
        }

        if (getBytes(imageFile) == null || isNotImageContentType(imageFile.getContentType()) || imageFile.isEmpty()) {
            return;
        }

        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = createFileName(originalFileName);

        try {
            createImageFile(imageFile, fileType, storeFileName);
        } catch (IOException e) {
            log.error("saveFile transferTo error = {}", imageFile.getName());
            return;
        }

        FileUploadDto fileUploadDto = FileUploadDto.builder()
                                                .parent(parentEntity)
                                                .imageUploadName(originalFileName)
                                                .imageStoreName(storeFileName)
                                                .type(fileType)
                                                .build();
        fileUploadRepository.saveImage(fileUploadDto);
    }

    // 신고하기 첨부파일은 썸네일을 적용하지 않음
    private void createImageFile(MultipartFile imageFile, FileUploadType fileType, String storeFileName) throws IOException {
        if (FileUploadType.NOTIFY_IMAGE.equals(fileType)) {
            imageFile.transferTo(new File(getFullPath(storeFileName, fileType)));
            return;
        }

        Thumbnails.of(imageFile.getInputStream())
            .size(100, 100)
            .toFile(new File(getFullPath(storeFileName, fileType)));
    }

    public void editFile(@Nullable MultipartFile imageFile, FileUploadType fileType, ImageFileEntity parentEntity) {
        if (imageFile == null) {
            FileUploadDto fileUploadDto = FileUploadDto.builder()
                .parent(parentEntity)
                .type(fileType)
                .build();
            fileUploadRepository.editImage(fileUploadDto);
            return;
        }

        if (getBytes(imageFile) == null || isNotImageContentType(imageFile.getContentType()) || imageFile.isEmpty()) {
            return;
        }

        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = createFileName(originalFileName);

        try {
            InputStream imgFileInputStream = imageFile.getInputStream();
            Thumbnails.of(imgFileInputStream)
                .size(100, 100)
                .toFile(new File(getFullPath(storeFileName, fileType)));
//            imageFile.transferTo(new File(getFullPath(storeFileName, fileType)));
        } catch (IOException e) {
            log.error("editFile transferTo error = {}", imageFile.getName());
            return;
        }

        // 이전 이미지 파일 삭제
        fileUploadRepository.deleteImage(fileType, parentEntity);

        FileUploadDto fileUploadDto = FileUploadDto.builder()
            .parent(parentEntity)
            .imageUploadName(originalFileName)
            .imageStoreName(storeFileName)
            .type(fileType)
            .build();

        fileUploadRepository.editImage(fileUploadDto);
    }

    private boolean isNotImageContentType(String contentType) {
        return contentType == null || !contentType.startsWith("image/");
    }

    private String getFullPath(String fileName, FileUploadType fileType) {
        StringBuilder sb = new StringBuilder(fileDir);

        if (fileType == null) return sb.append(fileName).toString();

        String folderName = fileType.getDir();
        return sb.append(folderName).append("/").append(fileName).toString();
    }

    private byte[] getBytes(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (IOException e) {
            log.error("saveFile getBytes error = {}", imageFile.getName());
            return null;
        }
    }

    @NotNull
    private String createFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();

        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos);

        return uuid + ext;
    }

}