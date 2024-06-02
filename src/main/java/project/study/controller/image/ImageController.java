package project.study.controller.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileService fileService;

    @GetMapping(value = "/images/{fileUploadType}/{filename}", produces = {"image/png", "image/jpg", "image/jpeg"})
    public Resource downloadImage1(@PathVariable(name = "fileUploadType") String fileUploadType, @PathVariable(name = "filename") String filename) throws MalformedURLException {
        FileUploadType type = FileUploadType.findDir(fileUploadType);
        return new UrlResource(fileService.getPath(filename, type));
    }
    @GetMapping("/images/{filename}")
    public Resource downloadImage2(@PathVariable(name = "filename") String filename) throws MalformedURLException {
        return new UrlResource(fileService.getPath(filename, null));
    }
}
