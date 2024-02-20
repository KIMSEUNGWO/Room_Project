package project.study.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatMemberListDto extends ChatDto {

    private List<String> currentMemberList;

}
