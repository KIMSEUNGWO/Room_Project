package project.study.controller.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.study.domain.ImageFileEntity;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServer implements FileStorage{

    private final FileService fileService;

    @Override
    public void uploadFile(MultipartFile imageFile, FileUploadType fileType, String storeFileName) throws IOException {
        String path = fileService.getPath(storeFileName, fileType);

        // 신고하기 첨부파일은 썸네일을 적용하지 않음
        if (FileUploadType.NOTIFY_IMAGE.equals(fileType)) {
            imageFile.transferTo(new File(path));
            return;
        }

        Thumbnails.of(imageFile.getInputStream())
            .size(100, 100)
            .toFile(path);
    }

    @Override
    public void deleteFile(FileUploadType type, ImageFileEntity parent) {
        if (parent.isDefaultImage()) return;

        String path = fileService.getPath(parent.getStoreImage(), type);
        File profileFile = new File(path);
        if (profileFile.delete()) {
            log.error("{} 파일 삭제", path);
        }
    }
}
