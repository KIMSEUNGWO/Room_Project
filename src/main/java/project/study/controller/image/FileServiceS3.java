package project.study.controller.image;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceS3 implements FileService {

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름
    private final AmazonS3 amazonS3;

    @Override
    public String getPath(String fileName, FileUploadType type) {
        StringBuilder sb = new StringBuilder("images/");

        if (type != null) {
            String folderName = type.getDir();
            sb.append(folderName).append("/");
        }
        sb.append(fileName);

        return amazonS3.getUrl(bucketName, sb.toString()).getPath();
    }
}
