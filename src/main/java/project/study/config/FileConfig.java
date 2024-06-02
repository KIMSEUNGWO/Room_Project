package project.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.study.controller.image.FileService;
import project.study.controller.image.FileServiceServer;
import project.study.controller.image.FileStorage;
import project.study.controller.image.FileStorageServer;

@Configuration
public class FileConfig {

    @Bean
    FileService fileService() {
        return new FileServiceServer();
    }

    @Bean
    FileStorage fileStorage() {
        return new FileStorageServer(fileService());
    }
}
