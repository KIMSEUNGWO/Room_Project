package project.study.dto.room;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import project.study.domain.RoomImage;
import project.study.domain.Tag;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateRoomDto {

    private Long roomId;

    private MultipartFile roomImageStoreName;

    @NotBlank
    @Length(min = 2, max = 10, message = "방 제목은 2글자 이상 10글자 이하로 작성해주세요")
    private String roomTitle;

    @Length(max = 50, message = "방 소개글은 최대 50글자까지만 가능합니다")
    private String roomIntro;

    @NotBlank(message = "필수 선택사항입니다")
    private String roomLimit;

    private List<Tag> tags;

}
