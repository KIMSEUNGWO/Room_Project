package project.study.dto.room;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestCreateRoomDto {

    private MultipartFile profile;
    private String title;
    private String intro;
    private String max;
    private List<String> tags;

}
