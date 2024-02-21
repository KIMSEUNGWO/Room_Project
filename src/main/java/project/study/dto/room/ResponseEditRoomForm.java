package project.study.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.study.dto.abstractentity.ResponseDto;
import project.study.enums.PublicEnum;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEditRoomForm {

    private String image;
    private String title;
    private String intro;
    private int max;
    private PublicEnum roomPublic;
    private String password;
    private List<String> tagList;

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
