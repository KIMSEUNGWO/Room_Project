package project.study.authority.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import project.study.enums.PublicEnum;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestEditRoomDto extends RequestCreateRoomDto {

    private boolean defaultImage;
}
