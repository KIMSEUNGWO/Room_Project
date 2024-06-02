package project.study.controller.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.study.domain.ImageFileEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageS3 implements FileStorage{

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름
    private final AmazonS3 amazonS3;
    private final FileService fileService;

    @Override
    public void uploadFile(MultipartFile imageFile, FileUploadType fileType, String storeFileName) throws IOException {
        File file = convert(imageFile, fileType).orElseThrow(IOException::new);
        String fullPath = fileService.getPath(storeFileName, fileType);
        amazonS3.putObject(new PutObjectRequest(bucketName, fullPath, file));
        file.delete();
    }

    @Override
    public void deleteFile(FileUploadType type, ImageFileEntity parent) {
        String path = String.format("/%s/%s", type.getDir(), parent.getStoreImage());
        amazonS3.deleteObject(bucketName, path);
    }

    private Optional<File> convert(MultipartFile multipartFile, FileUploadType fileType) throws IOException{
        File convertFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        if (convertFile.createNewFile()){

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());

                if (!FileUploadType.NOTIFY_IMAGE.equals(fileType)) { // NotifyImage가 아니면 썸네일로 저장
                    Thumbnails.of(multipartFile.getInputStream())
                        .size(100, 100)
                        .toFile(convertFile);
                }

                return Optional.of(convertFile);
            }
        }
        return Optional.empty();
    }
}
