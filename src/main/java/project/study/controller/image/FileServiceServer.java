package project.study.controller.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceServer implements FileService{

    @Value("${file.dir.server}")
    private String fileDir;

    @Override
    public String getPath(String fileName, FileUploadType type) {
        StringBuilder sb = new StringBuilder("file:").append(fileDir);

        if (type != null) {
            String folderName = type.getDir();
            sb.append(folderName);
        }

        return sb.append("/").append(fileName).toString();
    }
}
